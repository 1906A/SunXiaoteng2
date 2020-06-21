package com.leyou.search.client;

import com.leyou.client.BrandClientServer;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "item-server")
public interface BrandClient extends BrandClientServer {

}
