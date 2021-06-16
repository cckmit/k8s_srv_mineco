package com.egoveris.ccomplejos.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CC_DETAILEDA")
public class DetailEDA extends AbstractCComplejoJPA {

	@Column(name = "HS_CODE")
	String cmd;

	@Column(name = "ITEM_CODE")
	String cm2;

	@Column(name = "ITEM_NAME")
	String cmn;

	@Column(name = "ORIGIN_CODE")
	String or;

	@Column(name = "QUANTITY1")
	String qn1;

	@Column(name = "UNIT_CODE1")
	String qt1;

	@Column(name = "QUANTITY2")
	String qn2;

	@Column(name = "UNIT_CODE2")
	String qt2;

	@Column(name = "CMP")
	String cmp;

	@Column(name = "BP_CURRENCY")
	String bpc;

	@Column(name = "BASIC_PRICE")
	String bpk;

	@Column(name = "TAX_ER")
	String ect;

	@Column(name = "TAX_ER_AMOUNT")
	String eac;

	/**
	 * @return the cmd
	 */
	public String getCmd() {
		return cmd;
	}

	/**
	 * @param cmd
	 *            the cmd to set
	 */
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	/**
	 * @return the cm2
	 */
	public String getCm2() {
		return cm2;
	}

	/**
	 * @param cm2
	 *            the cm2 to set
	 */
	public void setCm2(String cm2) {
		this.cm2 = cm2;
	}

	/**
	 * @return the cmn
	 */
	public String getCmn() {
		return cmn;
	}

	/**
	 * @param cmn
	 *            the cmn to set
	 */
	public void setCmn(String cmn) {
		this.cmn = cmn;
	}

	/**
	 * @return the or
	 */
	public String getOr() {
		return or;
	}

	/**
	 * @param or
	 *            the or to set
	 */
	public void setOr(String or) {
		this.or = or;
	}

	/**
	 * @return the qn1
	 */
	public String getQn1() {
		return qn1;
	}

	/**
	 * @param qn1
	 *            the qn1 to set
	 */
	public void setQn1(String qn1) {
		this.qn1 = qn1;
	}

	/**
	 * @return the qt1
	 */
	public String getQt1() {
		return qt1;
	}

	/**
	 * @param qt1
	 *            the qt1 to set
	 */
	public void setQt1(String qt1) {
		this.qt1 = qt1;
	}

	/**
	 * @return the qn2
	 */
	public String getQn2() {
		return qn2;
	}

	/**
	 * @param qn2
	 *            the qn2 to set
	 */
	public void setQn2(String qn2) {
		this.qn2 = qn2;
	}

	/**
	 * @return the qt2
	 */
	public String getQt2() {
		return qt2;
	}

	/**
	 * @param qt2
	 *            the qt2 to set
	 */
	public void setQt2(String qt2) {
		this.qt2 = qt2;
	}

	/**
	 * @return the cmp
	 */
	public String getCmp() {
		return cmp;
	}

	/**
	 * @param cmp
	 *            the cmp to set
	 */
	public void setCmp(String cmp) {
		this.cmp = cmp;
	}

	/**
	 * @return the bpc
	 */
	public String getBpc() {
		return bpc;
	}

	/**
	 * @param bpc
	 *            the bpc to set
	 */
	public void setBpc(String bpc) {
		this.bpc = bpc;
	}

	/**
	 * @return the bpk
	 */
	public String getBpk() {
		return bpk;
	}

	/**
	 * @param bpk
	 *            the bpk to set
	 */
	public void setBpk(String bpk) {
		this.bpk = bpk;
	}

	/**
	 * @return the ect
	 */
	public String getEct() {
		return ect;
	}

	/**
	 * @param ect
	 *            the ect to set
	 */
	public void setEct(String ect) {
		this.ect = ect;
	}

	/**
	 * @return the eac
	 */
	public String getEac() {
		return eac;
	}

	/**
	 * @param eac
	 *            the eac to set
	 */
	public void setEac(String eac) {
		this.eac = eac;
	}

}
