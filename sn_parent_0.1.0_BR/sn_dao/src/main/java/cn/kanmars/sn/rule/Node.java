package cn.kanmars.sn.rule;

/**
 * Created by zhaojiuyang on 2015/12/10.
 */
public class Node {
    String name;
    String ip;
    public Node(String name,String ip) {
        this.name = name;
        this.ip = ip;
    }
    @Override
    public String toString() {
        return this.name+"-"+this.ip;
    }
}
