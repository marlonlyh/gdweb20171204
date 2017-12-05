package com.whir.ht.webservice.model.claimser;

/**
 * 车辆GPS信息
 * @author chenshaofeng
 * 注意: 文档标题与说明不一致
 */
public class CarGpsBean {
	private String id;// 编号
	private String carnum;// 车辆编号
	private String gpsno;// GPS编号
	private String time;// GPS时间
	private String address;// 当前详细位置
	private String status;// 车辆状态
	private String speed;// 车辆速度
	private String lat;// 纬度
	private String lng;// 经度
	private String temp1;// 车辆温度传感器1
	private String temp2;// 车辆温度传感器2
	private String  temp3;// 车辆温度传感器3
	private String temp4;// 车辆温度传感器4
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCarnum() {
		return carnum;
	}
	public void setCarnum(String carnum) {
		this.carnum = carnum;
	}
	public String getGpsno() {
		return gpsno;
	}
	public void setGpsno(String gpsno) {
		this.gpsno = gpsno;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSpeed() {
		return speed;
	}
	public void setSpeed(String speed) {
		this.speed = speed;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getTemp1() {
		return temp1;
	}
	public void setTemp1(String temp1) {
		this.temp1 = temp1;
	}
	public String getTemp2() {
		return temp2;
	}
	public void setTemp2(String temp2) {
		this.temp2 = temp2;
	}
	public String getTemp3() {
		return temp3;
	}
	public void setTemp3(String temp3) {
		this.temp3 = temp3;
	}
	public String getTemp4() {
		return temp4;
	}
	public void setTemp4(String temp4) {
		this.temp4 = temp4;
	}
	
}
