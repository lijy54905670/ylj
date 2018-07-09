package com.xinyuan.ms.service;

import com.xinyuan.ms.common.enums.OrderStatus;
import com.xinyuan.ms.common.util.ResultUtil;
import com.xinyuan.ms.common.web.Message;
import com.xinyuan.ms.entity.ProductOrder;
import com.xinyuan.ms.mapper.ProductOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author hwz
 */
@Service
public class ProductOrderService extends BaseService<ProductOrderRepository, ProductOrder, Long> {

    @Autowired
    private ProductOrderRepository productOrderRepository;



    private static long number;

    /**
     * 删除
     *
     * @param ids
     */
    @Transactional(rollbackFor = Exception.class)
    public void removeProductOrder(List<Long> ids) {
        for (Long i : ids) {
            super.remove(i);
        }
    }

    /**
     * 通过采样和编号查询
     *
     * @param collectionNumber
     * @return
     */
    public ProductOrder findByCollectionNumber(String collectionNumber) {
        return productOrderRepository.findByCollectionNumberAndDeleted(collectionNumber, 0);
    }


    /**
     * 通过id查询
     *
     * @param number
     * @return
     */
    public ProductOrder findByNumber(String number) {
        return productOrderRepository.findByNumberAndDeleted(number, 0);
    }





    /**
     * 查找所有
     *
     * @return
     */


    public Long findMaxNumber() {
        return productOrderRepository.findMaxNumber();
    }





    /**
     * 生成订单编号
     *
     * @return
     */
    public String orderNumber() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String s = formatter.format(new Date());
        String orderNumber;

        if (findMaxNumber() != null) {
            String max = findMaxNumber() / 10000 + "";
            if (!max.equals(s)) {
                number = 0;
            }
        }
        orderNumber = nextNumber();

        return orderNumber;
    }





    /**
     * 检测中
     *
     * @param productOrder
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Message check(ProductOrder productOrder) {
        Message message;

        //订单状态改为检测中
        productOrder.setStatus(OrderStatus.CHECKING.getId());
        productOrder.setCheckTime(new Timestamp(System.currentTimeMillis()).toString());

        update(productOrder);

        message = ResultUtil.success("检测中");

        return message;
    }

    /**
     * 报告已发布
     *
     * @param collectionNumber
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Message report(String collectionNumber) {
        Message message;

        ProductOrder productOrder = findByCollectionNumber(collectionNumber);
        //订单状态改为报告已发布
        productOrder.setStatus(OrderStatus.REPORT.getId());
        productOrder.setReportTime(new Timestamp(System.currentTimeMillis()).toString());

        update(productOrder);

        message = ResultUtil.success("报告已发布");

        return message;
    }





    public static synchronized String nextNumber() {
        number++;
        String str = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        long m = Long.parseLong((str)) * 10000;
        m += number;
        return m + "";
    }


}
