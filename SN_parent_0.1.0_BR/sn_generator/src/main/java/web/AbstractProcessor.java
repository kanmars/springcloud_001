package web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by baolong on 2016/8/23.
 */
public abstract class AbstractProcessor {
    public abstract void process(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);
}
