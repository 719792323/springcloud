package sj.springcloud.seata.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sj.springcloud.seata.dao.StorageDao;

import javax.annotation.Resource;

@Service
@Slf4j
public class StorageServiceImpl implements StorageService {

    @Resource
    private StorageDao storageDao;

    /**
     * 扣减库存
     * @param productId
     * @param count
     */
    @Override
    public void decrease(Long productId, Integer count) {
        log.info("-------->storage-service中扣减库存");
//        LOGGER.info("-------->storage-service中扣减库存");
        storageDao.decrease(productId, count);
    }
}
