package tech.btzstudio.family.auth;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;

@Component
public class Query implements GraphQLQueryResolver {

    public String hello() {
        return "Hello";
    }
}
