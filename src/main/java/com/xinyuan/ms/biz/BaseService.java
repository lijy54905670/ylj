package com.xinyuan.ms.biz;


import com.xinyuan.ms.dao.BaseJpaRepository;
import com.xinyuan.ms.entity.PageBean;
import com.xinyuan.ms.entity.ParamCondition;
import com.xinyuan.ms.entity.SelectParam;
import com.xinyuan.ms.exception.BaseException;
import com.xinyuan.ms.util.EntityUtils;
import com.xinyuan.ms.util.ReflectionUtils;
import com.xinyuan.ms.web.req.Conditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 基础Service层
 *
 * @author
 * @since 2018-03-06
 */
public abstract class BaseService<J extends BaseJpaRepository<T, ID>, T, ID extends Serializable> {

    @Autowired
    protected J bizRepository;

    @Autowired
    private EntityManager entityManager;

    /**
     * 业务新增方法(初始化和校验)
     *
     * @author 2018-03-06 14:00
     */
    public T save(T entity) throws BaseException {
        EntityUtils.setBizCreateInfo(entity);
        T jpaResult = bizRepository.saveAndFlush(entity);
        entityManager.clear();  //清空一级缓存
        T result = null;

        String fieldName = "id";
        if (ReflectionUtils.hasField(jpaResult, fieldName)) {
            ID id = (ID) ReflectionUtils.getFieldValue(jpaResult, "id");
            result = bizRepository.findOne(id);
        }
        return result;
    }

    /**
     * 业务删除方法(初始化和校验)
     *
     * @author 2018-03-06 14:01
     */
    public void remove(ID id) throws BaseException {
        T entity = bizRepository.findOne(id);
        if (entity != null) {
            if (ReflectionUtils.hasField(entity, "deleted")) {
                ReflectionUtils.invokeSetter(entity, "deleted", 1);
            }
            EntityUtils.setBizLogicDeleteInfo(entity);
            bizRepository.save(entity);
        }
    }

    /**
     * 业务更新方法(初始化和校验)
     *
     * @author 2018-03-06 14:59
     */
    public void update(T entity) throws BaseException {
        T result = null;
        if (ReflectionUtils.hasField(entity, "id")) {
            ID id = (ID) ReflectionUtils.getFieldValue(entity, "id");
            result = bizRepository.findOne(id);
        }
        EntityUtils.copyPropertiesIgnoreNull(entity, result);
        EntityUtils.setBizUpdatedInfo(result);
        bizRepository.saveAndFlush(result);
    }

    /**
     * 查询单个方法
     *
     * @author 2018-03-06 14:59
     */
    public T get(ID id) throws BaseException {
        return bizRepository.findOne(id);
    }

    /**
     * 业务条件查询方法(带分页参数)
     *
     * @author 2018-03-08 9:17
     */
    public Page findByCondition(Integer pageNum, Integer pageSize, List<SelectParam> selectParams) {
        int page = 1;
        int limit = 10;
        if (pageNum != null) {
            page = pageNum;
        }
        if (pageSize != null) {
            limit = pageSize;
        }

        Specification querySpecifi = getSpecification(selectParams, false);
        PageBean pageBean = new PageBean(page, limit, null);
        return bizRepository.findAll(querySpecifi, pageBean);
    }

    /**
     * 不带分页条件查询
     *
     * @author 2018-03-13 16:03
     */
    public List<T> findByCondition(List<SelectParam> selectParams) {
        return bizRepository.findAll(getSpecification(selectParams, false));
    }

    /**
     * 非业务表的条件查询(不带deleted字段的条件查询)
     *
     * @author 2018-03-21 18:59
     */
    public List<T> findByConditionAndDelete(List<SelectParam> selectParams) {
        return bizRepository.findAll(getSpecification(selectParams, true));
    }

    /**
     * 封装Specification对象
     *
     * @author 2018-03-13 16:04
     */
    private Specification getSpecification(List<SelectParam> selectParams, boolean isDelete) {
        return (Specification<T>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (!isDelete) {
                predicates.add(criteriaBuilder.equal(root.get("deleted"), 0));
            }
            if (selectParams != null) {
                for (SelectParam s : selectParams) {
                    switch (s.getCondition()) {
                        case EQUAL:
                            predicates.add(criteriaBuilder.equal(root.get(s.getParamKey()),
                                    s.getParamValue()));
                            break;
                        case GREATERTHAN:
                            predicates.add(criteriaBuilder.greaterThan(root.get(s.getParamKey()),
                                    (Comparable) s.getParamValue()));
                            break;
                        case LESSTHAN:
                            predicates.add(criteriaBuilder.lessThan(root.get(s.getParamKey()),
                                    (Comparable) s.getParamValue()));
                            break;
                        case LIKE:
                            predicates.add(criteriaBuilder.like(root.get(s.getParamKey()),
                                    "%" + s.getParamValue() + "%"));
                            break;
                        case GREATERTHANEQUAL:
                            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(s.getParamKey()),
                                    (Comparable) s.getParamValue()));
                            break;
                        case LESSTHANEQUAL:
                            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(s.getParamKey()),
                                    (Comparable) s.getParamValue()));
                            break;
                        default:
                            break;
                    }
                }
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }


    /**
     * 通用条件查询
     *
     * @param list
     * @return
     */
    public List<SelectParam> getSelectParamList(ArrayList<Conditions> list) {
        List<SelectParam> selectParamList = new ArrayList<>();
        if (list != null) {
            for (Conditions mapBean : list) {
                ParamCondition paramCondition = null;
                String condition = mapBean.getCondition();
                switch (condition) {
                    case "GREATERTHAN":
                        paramCondition = ParamCondition.GREATERTHAN;
                        break;
                    case "LESSTHAN":
                        paramCondition = ParamCondition.LESSTHAN;
                        break;
                    case "LIKE":
                        paramCondition = ParamCondition.LIKE;
                        break;
                    case "GREATERTHANEQUAL":
                        paramCondition = ParamCondition.GREATERTHANEQUAL;
                        break;
                    case "LESSTHANEQUAL":
                        paramCondition = ParamCondition.LESSTHANEQUAL;
                        break;
                    default:
                        paramCondition = ParamCondition.EQUAL;
                        break;
                }
                SelectParam selectParam = new SelectParam(mapBean.getKey(), mapBean.getValue(), paramCondition);
                selectParamList.add(selectParam);
            }
        }
        return selectParamList;
    }

}
