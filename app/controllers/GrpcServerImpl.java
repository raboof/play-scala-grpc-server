package controllers;

import java.lang.annotation.*;
import javax.inject.Qualifier;
import static java.lang.annotation.ElementType.*;

@Target({METHOD, FIELD, PARAMETER, TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Qualifier
public @interface GrpcServerImpl {

}