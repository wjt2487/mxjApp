package cn.mxj.mxjapp.util;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 现金流量表 ：公式运算
 * @author root
 *
 */
public class FormulaCalculation {
	public static JSONObject cBean ;
	/**
	 * 处理资产负债表数据
	 */
	public JSONObject operationByBalanceSheet(JSONObject bBean,JSONObject sBean, ArrayList<JSONObject> cashFlowList) {
		try {
			cBean = cashFlowList.get(sBean.getInt("sortIndex")-1);//因为list是从0开始
			//System.out.println(cBean.getId() + cBean.getString("itemName") + cBean.getInt("dragNumber"));
			double monthTotal = Double.parseDouble(sBean.getString("monthTotal"));		//现金流量表数据
			double sMonthTotal = Double.parseDouble(bBean.getString("monthTotal"));		//资产负债表 年初数
			double sYearTotal = Double.parseDouble(bBean.getString("yearTotal"));		//资产负债表 期末数
			
			//计算：销售商品、提供劳务收到的现金
			if (sBean.getInt("sortIndex") == 2) {
				if (bBean.getInt("sortIndex") < 37) {
					monthTotal = monthTotal + sMonthTotal - sYearTotal;
				}else {
					monthTotal = monthTotal + sYearTotal - sMonthTotal;
				}
			}
			
			
			//计算：购买商品、接受劳务支付的现金
			else if(sBean.getInt("sortIndex") == 6){
				if (bBean.getInt("sortIndex") < 37) {
					if (bBean.getInt("sortIndex") == 9) {	//如果拖拽为：存货
						if (sBean.getInt("companyId") == 5) { /**利达酒店，不需要乘以1.17*/
							monthTotal = monthTotal + sYearTotal - sMonthTotal;
						} else {
							monthTotal = monthTotal + (sYearTotal - sMonthTotal) * 1.17;
						}
					}else {
						monthTotal = monthTotal + sYearTotal - sMonthTotal;
					}
				}else {
					monthTotal = monthTotal + sMonthTotal - sYearTotal;
				}
			}
			
			//计算：支付的其他与经营活动有关的现金
			else if(sBean.getInt("sortIndex") == 9){
				monthTotal = monthTotal -( sYearTotal - sMonthTotal);
			}
			
			//计算：购建固定资产、无形资产和其他长期资产所支付的现金
			else if(sBean.getInt("sortIndex") == 18){
				monthTotal = monthTotal + sYearTotal - sMonthTotal;
			}
			
			//计算：借款所收到的现金
			else if (sBean.getInt("sortIndex") == 25) {
				monthTotal = monthTotal + sYearTotal - sMonthTotal;
				if (monthTotal < 0) {
					monthTotal = 0;
				}
			}
			
			//计算：现金的期末余额
			else if (sBean.getInt("sortIndex") == 59) {
				monthTotal = monthTotal + sYearTotal;
			}
			//计算：减：现金的期初余额
			else if (sBean.getInt("sortIndex") == 60) {
				monthTotal = monthTotal + sMonthTotal;
			}
			//计算：固定资产折旧
			else if(sBean.getInt("sortIndex") == 39){
				monthTotal = monthTotal + sYearTotal -sMonthTotal;
			}
			else{
				if (bBean.getInt("sortIndex") < 37) {
					monthTotal = monthTotal + sMonthTotal - sYearTotal;
				}else {
					monthTotal = monthTotal + sYearTotal - sMonthTotal;
				}
			}
			
			sBean.put("monthTotal", monthTotal);
			sBean.put("dragNumber", (sBean.getInt("dragNumber")+1)); //拖拽次数加1，达到标准答案指定次数，则本操作完成
			if (cBean.getInt("dragNumber") == sBean.getInt("dragNumber")) {
				sBean.put("finish", true);
			}
			String str = sBean.getString("finishState");
			if (str == null || str == "") {
				sBean.put("finishState", bBean.get("itemName") + ";");//未拖过的，操作成功后将其加入
			} else {
				sBean.put("finishState", str + bBean.get("itemName") + ";");		//拖过的，操作成功后将其余加入

			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return sBean;
	}
	
	/**
	 * 处理利润表数据
	 */
	public JSONObject operationByProfit(JSONObject pBean,JSONObject sBean, ArrayList<JSONObject> cashFlowList) {
		try {
			cBean = cashFlowList.get(sBean.getInt("sortIndex")-1);
			double monthTotal = Double.parseDouble(sBean.getString("monthTotal"));	/**现金流量表数据*/
//			double sMonthTotal = Double.parseDouble(pBean.getString("monthTotal"));	/**利润表本月数据*/
			double sYearTotal = Double.parseDouble(pBean.getString("yearTotal"));	/**利润表本年数据*/
			
			//计算：销售商品、提供劳务收到的现金 2
			//计算：购买商品、接受劳务支付的现金 6
			if (sBean.getInt("sortIndex") == 2 || sBean.getInt("sortIndex") == 6) {
				if (sBean.getInt("companyId") == 5) { /**利达酒店 不需要乘以1.17*/
					monthTotal = monthTotal + sYearTotal;
				} else {
					monthTotal = monthTotal + sYearTotal * 1.17;
				}
			}else {
				monthTotal = monthTotal + sYearTotal;
			}
			
			sBean.put("monthTotal", monthTotal);
			sBean.put("dragNumber", (sBean.getInt("dragNumber")+1)); //拖拽次数加1，达到标准答案指定次数，则本操作完成
			if (cBean.getInt("dragNumber") == sBean.getInt("dragNumber")) {
				sBean.put("finish", true);
			}
			String str = sBean.getString("finishState");
			if (str == null || str == "") {
				sBean.put("finishState", pBean.get("itemName") + ";");//未拖过的，操作成功后将其加入
			} else {
				sBean.put("finishState", str + pBean.get("itemName") + ";");		//拖过的，操作成功后将其余加入

			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return sBean;
	}
	
	/**
	 * 处理辅助数据表数据
	 */
	public JSONObject operationByAuxiliaryData(JSONObject aBean,JSONObject sBean, ArrayList<JSONObject> cashFlowList) {
		try {
			cBean = cashFlowList.get(sBean.getInt("sortIndex")-1);
			double monthTotal = Double.parseDouble(sBean.getString("monthTotal"));	/**现金流量表数据*/
			double sYearTotal = Double.parseDouble(aBean.getString("yearTotal"));	/**表外数据本年数据*/
			
			if (sBean.getInt("sortIndex") == 9) {
				monthTotal = monthTotal - sYearTotal;		//计算：支付的其他与经营活动有关的现金 
			}else {
				monthTotal = monthTotal + sYearTotal;
			}
			
			sBean.put("monthTotal", monthTotal);
			sBean.put("dragNumber", (sBean.getInt("dragNumber")+1)); //拖拽次数加1，达到标准答案指定次数，则本操作完成
			if (cBean.getInt("dragNumber") == sBean.getInt("dragNumber")) {
				sBean.put("finish", true);
			}
			String str = sBean.getString("finishState");
			if (str == null || str == "") {
				sBean.put("finishState", aBean.get("itemName") + ";");//未拖过的，操作成功后将其加入
			} else {
				sBean.put("finishState", str + aBean.get("itemName") + ";");		//拖过的，操作成功后将其余加入

			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return sBean;
	}
}
