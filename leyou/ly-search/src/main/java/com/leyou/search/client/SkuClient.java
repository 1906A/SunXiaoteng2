package com.leyou.search.client;

import com.leyou.client.SkuClientServer;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "item-server")
public interface SkuClient extends SkuClientServer {

}
