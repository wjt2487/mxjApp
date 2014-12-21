package cn.mxj.mxjapp.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AlertInfoLogic {
		ArrayList<Map<String,String>> list = null;
		public  AlertInfoLogic(){
			list = new ArrayList<Map<String,String>>();
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("data", "3");
			map.put("label", "2,3,10,22,32,33,63");
			list.add(map);
			
			map = new HashMap<String, String>();
			map.put("data", "4");
			map.put("label", "2,3,4");
			list.add(map);
			
			map = new HashMap<String, String>();
			map.put("data", "8");
			map.put("label", "7,29");
			list.add(map);
			
			map = new HashMap<String, String>();
			map.put("data", "9");
			map.put("label", "6,7,8,9");
			list.add(map);
			
			map = new HashMap<String, String>();
			map.put("data", "10");
			map.put("label", "5,10");
			list.add(map);
			map = new HashMap<String, String>();
			map.put("data", "16");
			map.put("label", "13,14,15,16");
			list.add(map);
			
			map = new HashMap<String, String>();
			map.put("data", "20");
			map.put("label", "18,19,20");
			list.add(map);
			
			map = new HashMap<String, String>();
			map.put("data", "21");
			map.put("label", "17,21");
			list.add(map);
			
			map = new HashMap<String, String>();
			map.put("data", "26");
			map.put("label", "24,25,26");
			list.add(map);
			
			map = new HashMap<String, String>();
			map.put("data", "30");
			map.put("label", "28,29,30");
			list.add(map);
			
			map = new HashMap<String, String>();
			map.put("data", "31");
			map.put("label", "27,31");
			list.add(map);
			
			map = new HashMap<String, String>();
			map.put("data", "33");
			map.put("label", "11,22,32,33");
			list.add(map);
			
			map = new HashMap<String, String>();
			map.put("data", "51");
			map.put("label", "11,37,38,39,40,42,43,45,\n46,48,49,50,51");
			list.add(map);
			
			map = new HashMap<String, String>();
			map.put("data", "52");
			map.put("label", "37,38,39,40,41,42,43,44,\n45,46,47,48,49,50,51,52");
			list.add(map);
			
		}
		
		
		public String getLableByData(int data){
			String label = "";
			for(int i = 0;i < list.size();i++){
				int index = Integer.parseInt(list.get(i).get("data"));
				if(data == index){
					label= list.get(i).get("label");
				}
			}
			return label;
		}
}
