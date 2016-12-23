package cn.com.xcommon.frame.cache;

import java.io.Serializable;

/**
 * @ClassName: IdValue
 * @Description: 数据字典实体
 */
public class IdValue implements Serializable {

	private static final long serialVersionUID = 6526582447308162027L;

	private String id;

	private String value;
	
	private boolean selected;

	public IdValue(String id, String value) {
		super();
		this.id = id;
		this.value = value;
	}
	
	public IdValue(String id, String value , boolean selected) {
		super();
		this.id = id;
		this.value = value;
		this.selected = selected;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}
