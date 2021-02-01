package com.egoveris.ccomplejos.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="CC_COMMONEDA")
public class CommonEDA extends AbstractCComplejoJPA {
	
	@Column(name="OTHER_TAXES")
	String otc;

	@Column(name="DECLARATION_NUMBER")
	String ecn;

	@Column(name="TYPE_EXPORT")
	String ecb;

	@Column(name="CARGO_CATEGORY")
	String ic2;

	@Column(name="TRANSACTION_NATURE")
	String ntc;

	@Column(name="EXPORTER_TYPE")
	String skb;

	@Column(name="TRANSPORT_MODE")
	String mtc;

	@Column(name="CUSTOMS_STATION")
	String ch;

	@Column(name="EXPORTER_CODE")
	String emc;

	@Column(name="EXPORTER_DESC")
	String emn;

	@Column(name="EXPORTER_ADDRESS")
	String ema;

	@Column(name="EXPORTER_ZIP")
	String emy;

	@Column(name="EXPORTER_PHONE")
	String emt;

	@Column(name="CUSTOMS_WAREHOUSE")
	String st;

	@Column(name="PLANNED_DECLARANT")
	String ecc;

	@Column(name="CONSIGNEE_CODE")
	String cgn;

	@Column(name="CONSIGNEE_ADDRESS")
	String cgp;

	@Column(name="CONSIGNEE_ZIP")
	String cga;

	@Column(name="CONSIGNEE_COUNTRY")
	String cgk;

	@Column(name="AWB")
	String awb;

	@Column(name="PACKAGES_NUMBER")
	String no;

	@Column(name="GROSS_WEIGHT")
	String gw;

	@Column(name="CONVEYANCE")
	String vsn;

	@Column(name="DEPARTURE_DATE")
	Date sym;

	@Column(name="LOADING_LOCATION")
	String psc;

	@Column(name="FINAL_DESTINATION")
	String dsc;

	@Column(name="INVOICE_PRICE")
	String ip1;

	@Column(name="INCOTERMS")
	String ip2;

	@Column(name="INVOICE_CURRENCY")
	String ip3;

	@Column(name="INVOICE_TOTAL")
	String ip4;

	@Column(name="LC")
	String lc;

	@Column(name="LCN")
	String lcn;

	@Column(name="LCD")
	String lcd;

	@Column(name="BAN")
	String ban;

	@Column(name="AUTHENTICATION")
	String aut;

	/**
	 * @return the otc
	 */
	public String getOtc() {
		return otc;
	}

	/**
	 * @param otc the otc to set
	 */
	public void setOtc(String otc) {
		this.otc = otc;
	}

	/**
	 * @return the ecn
	 */
	public String getEcn() {
		return ecn;
	}

	/**
	 * @param ecn the ecn to set
	 */
	public void setEcn(String ecn) {
		this.ecn = ecn;
	}

	/**
	 * @return the ecb
	 */
	public String getEcb() {
		return ecb;
	}

	/**
	 * @param ecb the ecb to set
	 */
	public void setEcb(String ecb) {
		this.ecb = ecb;
	}

	/**
	 * @return the ic2
	 */
	public String getIc2() {
		return ic2;
	}

	/**
	 * @param ic2 the ic2 to set
	 */
	public void setIc2(String ic2) {
		this.ic2 = ic2;
	}

	/**
	 * @return the ntc
	 */
	public String getNtc() {
		return ntc;
	}

	/**
	 * @param ntc the ntc to set
	 */
	public void setNtc(String ntc) {
		this.ntc = ntc;
	}

	/**
	 * @return the skb
	 */
	public String getSkb() {
		return skb;
	}

	/**
	 * @param skb the skb to set
	 */
	public void setSkb(String skb) {
		this.skb = skb;
	}

	/**
	 * @return the mtc
	 */
	public String getMtc() {
		return mtc;
	}

	/**
	 * @param mtc the mtc to set
	 */
	public void setMtc(String mtc) {
		this.mtc = mtc;
	}

	/**
	 * @return the ch
	 */
	public String getCh() {
		return ch;
	}

	/**
	 * @param ch the ch to set
	 */
	public void setCh(String ch) {
		this.ch = ch;
	}

	/**
	 * @return the emc
	 */
	public String getEmc() {
		return emc;
	}

	/**
	 * @param emc the emc to set
	 */
	public void setEmc(String emc) {
		this.emc = emc;
	}

	/**
	 * @return the emn
	 */
	public String getEmn() {
		return emn;
	}

	/**
	 * @param emn the emn to set
	 */
	public void setEmn(String emn) {
		this.emn = emn;
	}

	/**
	 * @return the ema
	 */
	public String getEma() {
		return ema;
	}

	/**
	 * @param ema the ema to set
	 */
	public void setEma(String ema) {
		this.ema = ema;
	}

	/**
	 * @return the emy
	 */
	public String getEmy() {
		return emy;
	}

	/**
	 * @param emy the emy to set
	 */
	public void setEmy(String emy) {
		this.emy = emy;
	}

	/**
	 * @return the emt
	 */
	public String getEmt() {
		return emt;
	}

	/**
	 * @param emt the emt to set
	 */
	public void setEmt(String emt) {
		this.emt = emt;
	}

	/**
	 * @return the st
	 */
	public String getSt() {
		return st;
	}

	/**
	 * @param st the st to set
	 */
	public void setSt(String st) {
		this.st = st;
	}

	/**
	 * @return the ecc
	 */
	public String getEcc() {
		return ecc;
	}

	/**
	 * @param ecc the ecc to set
	 */
	public void setEcc(String ecc) {
		this.ecc = ecc;
	}

	/**
	 * @return the cgn
	 */
	public String getCgn() {
		return cgn;
	}

	/**
	 * @param cgn the cgn to set
	 */
	public void setCgn(String cgn) {
		this.cgn = cgn;
	}

	/**
	 * @return the cgp
	 */
	public String getCgp() {
		return cgp;
	}

	/**
	 * @param cgp the cgp to set
	 */
	public void setCgp(String cgp) {
		this.cgp = cgp;
	}

	/**
	 * @return the cga
	 */
	public String getCga() {
		return cga;
	}

	/**
	 * @param cga the cga to set
	 */
	public void setCga(String cga) {
		this.cga = cga;
	}

	/**
	 * @return the cgk
	 */
	public String getCgk() {
		return cgk;
	}

	/**
	 * @param cgk the cgk to set
	 */
	public void setCgk(String cgk) {
		this.cgk = cgk;
	}

	/**
	 * @return the awb
	 */
	public String getAwb() {
		return awb;
	}

	/**
	 * @param awb the awb to set
	 */
	public void setAwb(String awb) {
		this.awb = awb;
	}

	/**
	 * @return the no
	 */
	public String getNo() {
		return no;
	}

	/**
	 * @param no the no to set
	 */
	public void setNo(String no) {
		this.no = no;
	}

	/**
	 * @return the gw
	 */
	public String getGw() {
		return gw;
	}

	/**
	 * @param gw the gw to set
	 */
	public void setGw(String gw) {
		this.gw = gw;
	}

	/**
	 * @return the vsn
	 */
	public String getVsn() {
		return vsn;
	}

	/**
	 * @param vsn the vsn to set
	 */
	public void setVsn(String vsn) {
		this.vsn = vsn;
	}

	/**
	 * @return the sym
	 */
	public Date getSym() {
		return sym;
	}

	/**
	 * @param sym the sym to set
	 */
	public void setSym(Date sym) {
		this.sym = sym;
	}

	/**
	 * @return the psc
	 */
	public String getPsc() {
		return psc;
	}

	/**
	 * @param psc the psc to set
	 */
	public void setPsc(String psc) {
		this.psc = psc;
	}

	/**
	 * @return the dsc
	 */
	public String getDsc() {
		return dsc;
	}

	/**
	 * @param dsc the dsc to set
	 */
	public void setDsc(String dsc) {
		this.dsc = dsc;
	}

	/**
	 * @return the ip1
	 */
	public String getIp1() {
		return ip1;
	}

	/**
	 * @param ip1 the ip1 to set
	 */
	public void setIp1(String ip1) {
		this.ip1 = ip1;
	}

	/**
	 * @return the ip2
	 */
	public String getIp2() {
		return ip2;
	}

	/**
	 * @param ip2 the ip2 to set
	 */
	public void setIp2(String ip2) {
		this.ip2 = ip2;
	}

	/**
	 * @return the ip3
	 */
	public String getIp3() {
		return ip3;
	}

	/**
	 * @param ip3 the ip3 to set
	 */
	public void setIp3(String ip3) {
		this.ip3 = ip3;
	}

	/**
	 * @return the ip4
	 */
	public String getIp4() {
		return ip4;
	}

	/**
	 * @param ip4 the ip4 to set
	 */
	public void setIp4(String ip4) {
		this.ip4 = ip4;
	}

	/**
	 * @return the lc
	 */
	public String getLc() {
		return lc;
	}

	/**
	 * @param lc the lc to set
	 */
	public void setLc(String lc) {
		this.lc = lc;
	}

	/**
	 * @return the lcn
	 */
	public String getLcn() {
		return lcn;
	}

	/**
	 * @param lcn the lcn to set
	 */
	public void setLcn(String lcn) {
		this.lcn = lcn;
	}

	/**
	 * @return the lcd
	 */
	public String getLcd() {
		return lcd;
	}

	/**
	 * @param lcd the lcd to set
	 */
	public void setLcd(String lcd) {
		this.lcd = lcd;
	}

	/**
	 * @return the ban
	 */
	public String getBan() {
		return ban;
	}

	/**
	 * @param ban the ban to set
	 */
	public void setBan(String ban) {
		this.ban = ban;
	}

	/**
	 * @return the aut
	 */
	public String getAut() {
		return aut;
	}

	/**
	 * @param aut the aut to set
	 */
	public void setAut(String aut) {
		this.aut = aut;
	}
		
}