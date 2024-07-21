package cz.cvut.nss.investmentmanagementsystem.config;

import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionConfig;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizePolicy;
import com.hazelcast.config.NetworkConfig;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class HazelcastConfig {

    @Bean
    public Config hazelcastConfiguration() {
        Config config = new Config();
        config.setInstanceName("hazelcast-instance");

        NetworkConfig networkConfig = config.getNetworkConfig();
        networkConfig.setPort(5701).setPortCount(20);

        networkConfig.getJoin().getMulticastConfig().setEnabled(false);
        networkConfig.getJoin().getTcpIpConfig().setEnabled(true)
                .addMember("192.168.1.100")
                .addMember("192.168.1.101");

        MapConfig marketDataCacheConfig = new MapConfig();
        marketDataCacheConfig.setName("marketDataCache");
        marketDataCacheConfig.setTimeToLiveSeconds(3600);
        marketDataCacheConfig.setMaxIdleSeconds(1800);
        marketDataCacheConfig.setEvictionConfig(new EvictionConfig()
                .setSize(1000)
                .setMaxSizePolicy(MaxSizePolicy.USED_HEAP_SIZE)
                .setEvictionPolicy(EvictionConfig.DEFAULT_EVICTION_POLICY));
        config.addMapConfig(marketDataCacheConfig);

        return config;
    }
}
