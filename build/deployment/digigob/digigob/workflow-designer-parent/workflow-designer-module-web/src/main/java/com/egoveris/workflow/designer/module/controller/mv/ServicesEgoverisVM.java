/**
 *
 */
package com.egoveris.workflow.designer.module.controller.mv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.terasoluna.plus.core.util.ApplicationContextUtil;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.egoveris.commons.databaseconfiguration.propiedades.AppProperty;
import com.egoveris.script.core.ScriptApi;
import com.egoveris.script.core.ScriptBase;
import com.egoveris.te.base.util.ConstantesServicios;

import io.swagger.models.Model;
import io.swagger.models.Path;
import io.swagger.models.Swagger;
import io.swagger.models.parameters.BodyParameter;
import io.swagger.models.parameters.Parameter;
import io.swagger.models.parameters.QueryParameter;
import io.swagger.models.properties.ArrayProperty;
import io.swagger.models.properties.Property;
import io.swagger.models.properties.RefProperty;
import io.swagger.parser.SwaggerParser;

public class ServicesEgoverisVM {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(ServicesEgoverisVM.class);
	@Wire("#servicesEgoveris")
	private Window win;
	@Wire("#cbxServices")
	private Combobox cbxServices;
	private List<ScriptApi> listServicesRest;
	private ScriptApi apiSelected;
	private AppProperty dBProperty;
	@Wire("#rowParameters")
	private Row rowParameters;
	@Wire("#gridParameters")
	private Grid gridParameters;
	@Wire("#typeRequest")
	private Textbox tbxTypeRequest;
	private String typeRequest;
	private String parameterType;
	private Swagger swagger;
	private Map<String, Path> paths;
	private Entry<String,Path> endPointSelected;
	private String PARAM_REF = "ref";
	private String PARAM_DEFINITION = "#/definitions/";
	private String PARAM_ARRAY = "array";
	private String PARAM_QUERY = "query";
	private String PARAM_BODY = "body";


	@Init
	public void init() {
		ScriptBase scriptBase = ScriptBase.getInstance(getDbProperty());
		if (scriptBase != null && CollectionUtils.isNotEmpty(scriptBase.getAvailableApis())) {
			listServicesRest = scriptBase.getAvailableApis();
		}
	}

	@AfterCompose
	public void after(@ContextParam(ContextType.COMPONENT) Component component,
			@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);

	}

	@NotifyChange({ "paths", "apiSelected" })
	@Command
	public void onLoadAviablesEndPoints(@BindingParam("entry") Integer index) {
		if (index != null) {
			apiSelected = listServicesRest.get(index);
			try {
				String ip = apiSelected.getBaseUrl() + "" + apiSelected.getSwaggerLocation();
				RestTemplate restTemplate = new RestTemplate();
				restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
				restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
				Map<String, Object> map = new HashMap<>();
				if (logger.isInfoEnabled()) {
					logger.info("onLoadAviablesEndPoints(Integer) - Map<String,Object> map={}", map); //$NON-NLS-1$
				}

				ResponseEntity<String> responseBody = restTemplate.getForEntity(ip, String.class, map);
				String obj = (String) responseBody.getBody();
				SwaggerParser parser = new SwaggerParser();
				swagger = parser.parse(obj);
				paths = swagger.getPaths();
			} catch (Exception e) {
				logger.error("", e);
			}
		}

	}

	@NotifyChange({ "typeRequest", "parameterType" })
	@Command
	public void onLoadParameters() {
		if (endPointSelected != null) {
			gridParameters.getRows().getChildren().clear();
			if(gridParameters.getColumns() != null){
				gridParameters.getColumns().detach();
			}
			Path pathSelected = swagger.getPath(endPointSelected.getKey());
			if (pathSelected != null) {
				List<Parameter> parameters = new ArrayList<>();
				if(pathSelected.getGet() != null){
					parameters = pathSelected.getGet().getParameters();
					typeRequest = HttpMethod.GET.toString();
				} else if(pathSelected.getPost() != null){
					parameters = pathSelected.getPost().getParameters();
					typeRequest = HttpMethod.POST.toString();
				}
				for (Parameter p : parameters) {
					if(PARAM_QUERY.equalsIgnoreCase(p.getIn())){
						 QueryParameter queryParam = ((QueryParameter) p);
						 gridParameters.getRows().appendChild(createComponent(queryParam.getName(),queryParam.getType(),queryParam.getRequired()));
						 if(StringUtils.isBlank(parameterType) || PARAM_BODY.equalsIgnoreCase(parameterType)){
							 parameterType = PARAM_QUERY;
						 }
					} else {
						parameterType = PARAM_BODY;
						Model model = ((BodyParameter) p ).getSchema();
						findObjectModel(model.getReference().replace(PARAM_DEFINITION, "").trim(), gridParameters.getRows());
					}
				}
			}
		if(gridParameters.getColumns() != null){
			gridParameters.getColumns().detach();
		}
		Columns columnsNew = new Columns();
		gridParameters.appendChild(columnsNew);
		gridParameters.getColumns().appendChild(createColumn("10%"));
		rowParameters.setVisible(true);
		}
	}
	
	private Column createColumn(String width){
		Column c = new Column();
		c.setWidth(width);
		return c;
	}
	
	private Component findObjectModel(String modelName, Component component){
		if(component == null){
			component = new Rows();
		}
		Model modelSchema = swagger.getDefinitions().get(modelName);
		for(Entry<String,Property> m : modelSchema.getProperties().entrySet()){
			Property prop = m.getValue();
			if(PARAM_REF.equalsIgnoreCase(prop.getType())){
				 Grid grid = createGrid();
				 Row row = (Row) createComponent(m.getKey(), null, null);
				 row.appendChild(grid);
				 component.appendChild(row);
				 RefProperty ref = ((RefProperty) m.getValue());
				 findObjectModel(ref.getSimpleRef().replace(PARAM_DEFINITION, "").trim(), grid.getRows());
			} else  if(PARAM_ARRAY.equalsIgnoreCase(prop.getType())){
				 ArrayProperty propertyArray =  ((ArrayProperty) m.getValue());
				 RefProperty ref = ((RefProperty) propertyArray.getItems());
				 Grid grid = createGrid();
				 Row row = (Row) createComponent(m.getKey(), prop.getType(), null);
				 row.appendChild(grid);
				 component.appendChild(row);
				 findObjectModel(ref.getSimpleRef().replace(PARAM_DEFINITION, "").trim(), grid.getRows());
			} else {
				 String name = prop.getName() == null ? m.getKey() : prop.getName();
				 component.appendChild(createComponent(name, prop.getType(), prop.getRequired()));
			}
		}
		return component;
	}
	
	
	
	private Grid createGrid(){
		Grid grid = new Grid();
		grid.setWidth("100%");
		Rows rows= new Rows();
		grid.appendChild(rows);
		return grid;
	}
	
	private Component createComponent(String labelText, String type, Boolean requerido){
		Row row = new Row();
		if(StringUtils.isNotBlank(labelText) && StringUtils.isNotBlank(type) && !PARAM_ARRAY.equalsIgnoreCase(type)){
			Cell cellOne = new Cell();
			Cell cellTwo = new Cell();
			Label label = new Label(labelText);
			Component c = defineComponent(type, requerido);
			cellOne.appendChild(label);
			cellTwo.appendChild(c);
			cellOne.setWidth("100px");
			row.appendChild(cellOne);
			row.appendChild(cellTwo);
		} else {
			Cell cell = new Cell();
			if(StringUtils.isNotBlank(labelText)){
				Label label = new Label(labelText);
				label.setAttribute("header", Boolean.TRUE.toString());
				if(PARAM_ARRAY.equalsIgnoreCase(type)){
					label.setAttribute(PARAM_ARRAY, Boolean.TRUE.toString());
				}
				cell.setWidth("100px");
				cell.appendChild(label);
			}
			if(StringUtils.isNotBlank(type) && !PARAM_ARRAY.equalsIgnoreCase(type)){
				 Component c = defineComponent(type, requerido);
				 cell.appendChild(c);
			} 
			row.appendChild(cell);

		}
		
		return row;
	}
	
	private Component defineComponent(String type, boolean requerido) {
		String constraint = "no empty";
		//String constraintNumeric = ", /[0-9]{3}/ : Ingrese un valor numerico";
		if ("boolean".equalsIgnoreCase(type)) {
			Combobox c = new Combobox();
			c.appendItem(Boolean.TRUE.toString());
			c.appendItem(Boolean.FALSE.toString());
			return c;
		} else {
			Textbox tbx = new Textbox();
			if ("string".equalsIgnoreCase(type)) {
				tbx.setConstraint(requerido ? constraint : null);
			} else if ("integer".equalsIgnoreCase(type)) {
				tbx.setAttribute("numeric", Boolean.TRUE.toString());
				//tbx.setConstraint(requerido ? constraint.concat(constraintNumeric) : constraintNumeric);
				tbx.setConstraint(requerido ? constraint : null);
			}
			return tbx;

		}
	}

	// ================================
	// GETTERS AND SETTERS
	// ================================

	public AppProperty getDbProperty() {
		if (dBProperty == null) {
			dBProperty = (AppProperty) ApplicationContextUtil.getBean(ConstantesServicios.APP_PROPERTY);
		}
		return dBProperty;
	}

	public Window getWin() {
		return win;
	}

	public void setWin(Window win) {
		this.win = win;
	}

	public Combobox getCbxServices() {
		return cbxServices;
	}

	public void setCbxServices(Combobox cbxServices) {
		this.cbxServices = cbxServices;
	}

	public List<ScriptApi> getListServicesRest() {
		return listServicesRest;
	}

	public void setListServicesRest(List<ScriptApi> listServicesRest) {
		this.listServicesRest = listServicesRest;
	}

	public ScriptApi getApiSelected() {
		return apiSelected;
	}

	public void setApiSelected(ScriptApi apiSelected) {
		this.apiSelected = apiSelected;
	}

	public AppProperty getdBProperty() {
		return dBProperty;
	}

	public void setdBProperty(AppProperty dBProperty) {
		this.dBProperty = dBProperty;
	}

	public Row getRowParameters() {
		return rowParameters;
	}

	public void setRowParameters(Row rowParameters) {
		this.rowParameters = rowParameters;
	}

	public Grid getGridParameters() {
		return gridParameters;
	}

	public void setGridParameters(Grid gridParameters) {
		this.gridParameters = gridParameters;
	}

	public Textbox getTbxTypeRequest() {
		return tbxTypeRequest;
	}

	public void setTbxTypeRequest(Textbox tbxTypeRequest) {
		this.tbxTypeRequest = tbxTypeRequest;
	}

	public String getTypeRequest() {
		return typeRequest;
	}

	public void setTypeRequest(String typeRequest) {
		this.typeRequest = typeRequest;
	}

	public String getParameterType() {
		return parameterType;
	}

	public void setParameterType(String parameterType) {
		this.parameterType = parameterType;
	}

	public Swagger getSwagger() {
		return swagger;
	}

	public void setSwagger(Swagger swagger) {
		this.swagger = swagger;
	}

	public Map<String, Path> getPaths() {
		return paths;
	}

	public void setPaths(Map<String, Path> paths) {
		this.paths = paths;
	}

	public Entry<String,Path> getEndPointSelected() {
		return endPointSelected;
	}

	public void setEndPointSelected(Entry<String,Path> endPointSelected) {
		this.endPointSelected = endPointSelected;
	}

}
