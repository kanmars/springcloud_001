package cn.kanmars.sn.rule;

import java.util.ArrayList;
import java.util.List;

public class MyTbRule {
	static Shard sh_tb ;
	static{
		sh_tb = createShard(4);
	}
	public static Shard createShard(int nodeCount){
		Shard sh = new Shard();
		List<Node> shards = new ArrayList<Node>();
		for(int i=0;i<nodeCount;i++){
			String index = String.valueOf(i);
			Node node = new Node("g_tb_"+index, index);
			shards.add(node);
		}
		sh.setShards(shards);
		sh.init();
		return sh ;
	}
	public static int getIndex(Long key) {
		int index   =  sh_tb.getKey(String.valueOf(key));
		return index;
	}

	public static void main(String[] args) {
		getIndex(1501833357L);
		for (long i =0;i<10000;i++){
			getIndex(i);
		}
	}
}
