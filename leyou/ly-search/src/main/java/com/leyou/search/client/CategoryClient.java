package com.leyou.search.client;

import com.leyou.client.CategoryClientServer;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "item-server")
public interface CategoryClient extends CategoryClientServer {
}
