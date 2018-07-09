package com.xinyuan.ms.mapper;

import com.xinyuan.ms.entity.ProductOrder;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author liang
 */
@Repository
public interface ProductOrderRepository extends BaseJpaRepository<ProductOrder, Long> {

    /**
     * 通过id查找订单
     *
     * @param id
     * @param deleted
     * @return
     */
    ProductOrder findByIdAndDeleted(Long id, int deleted);

    /**
     * 通过number查找订单
     *
     * @param number
     * @param deleted
     * @return
     */
    ProductOrder findByNumberAndDeleted(String number, int deleted);

    /**
     * 查找所有订单
     *
     * @param deleted
     * @return
     */
    List<ProductOrder> findAllByDeleted(int deleted);

    /**
     * findMaxNumber
     *
     * @return
     */
    @Query(value = "select max(NUMBER ) from m_order ", nativeQuery = true)
    Long findMaxNumber();

    /**
     * findByOldIdAndDeleted
     *
     * @param oldId
     * @param deleted
     * @return
     */
    ProductOrder findByOldIdAndDeleted(Long oldId, int deleted);

    /**
     * 查询用户订单
     *
     * @param userId
     * @param deleted
     * @return
     */
    List<ProductOrder> findByUserIdAndDeleted(Long userId, int deleted);

    /**
     * findByCollectionNumberAndDeleted
     *
     * @param collectionNumber
     * @param deleted
     * @return
     */
    ProductOrder findByCollectionNumberAndDeleted(String collectionNumber, int deleted);

    /**
     * findByStatusAndDeleted
     * @param status
     * @param deleted
     * @return
     */
    List<ProductOrder> findByStatusAndDeleted(int status, int deleted);

}
