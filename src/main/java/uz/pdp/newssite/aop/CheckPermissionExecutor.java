package uz.pdp.newssite.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import uz.pdp.newssite.entity.User;
import uz.pdp.newssite.exceptions.ForbiddenExceptions;

@Component
@Aspect
public class CheckPermissionExecutor {

    @Before(value = "@annotation(checkPermission)")
    public void checkPermissionMyMethod(CheckPermission checkPermission){
        User user=(User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean exist=false;
        for (GrantedAuthority authority : user.getAuthorities()) {
            if (authority.getAuthority().equals(checkPermission.permission())){
                exist=true;
                break;
            }
        }
        if (!exist)
            throw new ForbiddenExceptions(checkPermission.permission(),"Not allowed!");
    }
}
