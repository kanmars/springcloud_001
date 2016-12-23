package cn.com.xcommon.frame.logic;

import cn.com.xcommon.frame.exception.SnCommonException;

/**
 * @ClassName: ILogics
 * @Description: 业务单元接口
 * @date 2015年1月23日 上午10:07:38
 */
public interface ILogics<T> {

	public ResultEnum exec(T t) throws SnCommonException;
}
