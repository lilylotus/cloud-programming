package cn.nihiltiy.diversity.multiple.datasource.starter;

import cn.nihiltiy.diversity.multiple.datasource.entity.Flower;
import cn.nihiltiy.diversity.multiple.datasource.mapper.FlowerMapper;
import cn.nihiltiy.diversity.multiple.datasource.service.MultipleDatasourceService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "cn.nihiltiy.diversity.multiple.datasource")
public class MultipleStarter {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(MultipleStarter.class, args);

        FlowerMapper mapper = ctx.getBean(FlowerMapper.class);
        Flower flower = mapper.searchById("00001");
        System.out.println("default " + flower);

        MultipleDatasourceService service = ctx.getBean(MultipleDatasourceService.class);

        Flower f2 = service.selectId("xxxxx");
        System.out.println("select " + f2);

        Flower flower2 = new Flower("xxxxx221234", "test", "tset", 20);
        Flower f3 = service.insert(flower2);
        System.out.println("write " + f3);

        ctx.registerShutdownHook();
    }

}
