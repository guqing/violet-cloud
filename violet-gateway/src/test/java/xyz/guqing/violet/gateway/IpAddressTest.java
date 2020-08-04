package xyz.guqing.violet.gateway;

import org.junit.jupiter.api.Test;
import xyz.guqing.violet.gateway.enhance.utils.RegionAddressUtils;


/**
 * @author guqing
 * @date 2020-07-18
 */
class IpAddressTest {
    @Test
    void test() {
        String cityInfo = RegionAddressUtils.getCityInfo("45.137.219.219");
        System.out.println(cityInfo);
    }
}
