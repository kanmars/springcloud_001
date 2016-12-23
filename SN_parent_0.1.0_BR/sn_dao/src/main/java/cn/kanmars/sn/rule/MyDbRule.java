package cn.kanmars.sn.rule;

import java.util.ArrayList;
import java.util.List;

public class MyDbRule {
	static Shard sh_db ;
	static{
		sh_db = createShard(4);
	}
	public static Shard createShard(int nodeCount){
		Shard sh = new Shard();
		List<Node> shards = new ArrayList<Node>();
		for(int i=0;i<nodeCount;i++){
			String index = String.valueOf(i);
			Node node = new Node("g_db_"+index, index);
			shards.add(node);
		}
		sh.setShards(shards);
		sh.init();
		return sh ;
	}
	public static int getIndex(Long key) {
		int index   =  sh_db.getKey(String.valueOf(key));
		return index;
	}


	public static void main(String[] args) {
		for (long i =0;i<10000;i++){
			getIndex(i);
		}
	}


}
