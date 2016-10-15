package com.globbypotato.rockhounding_chemistry.proxy;

import com.globbypotato.rockhounding_chemistry.ModContents;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerRenders() {
  		ModContents.registerRenders();
    }
}
