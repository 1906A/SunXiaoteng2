package com.leyou.search.client;

import com.leyou.client.SpuClientServer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@FeignClient(name = "item-server")
public interface SpuClient extends SpuClientServer {

}
