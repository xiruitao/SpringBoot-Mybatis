package com.miaosha.service;

import com.miaosha.service.model.PromoModel;
import org.springframework.stereotype.Service;

public interface PromoService {
    //根据itemID获取即将进行或正在进行秒杀活动
    PromoModel getPromoByItemId(Integer itemId);
}
