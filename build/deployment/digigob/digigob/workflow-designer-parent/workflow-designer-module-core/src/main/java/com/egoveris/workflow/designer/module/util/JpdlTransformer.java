/**
 * 
 */
package com.egoveris.workflow.designer.module.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.egoveris.workflow.designer.module.model.Fork;
import com.egoveris.workflow.designer.module.model.ForkTag;
import com.egoveris.workflow.designer.module.model.Join;
import com.egoveris.workflow.designer.module.model.JoinTag;
import com.egoveris.workflow.designer.module.model.Link;
import com.egoveris.workflow.designer.module.model.Project;
import com.egoveris.workflow.designer.module.model.State;
import com.egoveris.workflow.designer.module.model.Task;
import com.egoveris.workflow.designer.module.model.Transition;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;


public class JpdlTransformer {
	
	private final List<String> HARD_STATES=Arrays.asList("Tramitación Libre","Guarda Temporal");
	private final List<String> AUTOLOOP_STATES=Arrays.asList("Tramitación Libre");

	public List<Transition> findTransitions(Project project, final String stateId) {
		List<Transition> transitions= new ArrayList<Transition>();
		
		// normalizo los links dobles
		List<Link> lstLink = new ArrayList<Link>();
		for (Link link : project.getLinks()) {
			if (link.isDoubleLink()) {
				Link a = new Link(link.getId(),link.getOrigin(),link.getDestiny());
				Link b = new Link(link.getId()+"-9",link.getDestiny(),link.getOrigin());
				a.setDoubleLink(false);
				b.setDoubleLink(false);
				lstLink.add(a);
				lstLink.add(b);
			} else {
				lstLink.add(link);
			}
		}
		
		State state = null;
		Fork fork   = null;
		Join join   = null;
				
		for (Link link : lstLink) {
			if (link.getOrigin().equalsIgnoreCase(stateId)) {
				state = findStateById(project, link.getDestiny());					
				if(state == null){
					fork = findForkById(project, link.getDestiny());
					if(fork == null){
						join = findJoinById(project, link.getDestiny());
					}
				}
				
				Transition trans = null;
				
				if(state!=null){
					trans = makeTransition(state);
				}
				else{
					if(fork!=null){
						trans = makeForkTransition(fork);
					}else{
						if(join!=null){
							trans = makeJoinTransition(join);
						}
					}
				}
				if (trans!=null) transitions.add(trans);
			}
		}
		return transitions;
	}
	
	public State findStateById(Project project, String idState) {
		if (idState.equalsIgnoreCase(project.getStartState().getAttributes().getId())) return project.getStartState();
		if (idState.equalsIgnoreCase(project.getEndState().getAttributes().getId())) return project.getEndState();
		for (State state : project.getStates()) {
			if (state.getAttributes().getId().equalsIgnoreCase(idState)) return state;
		}
		return null;
	}
	
	public Fork findForkById(Project project, String idFork) {
		for (Fork fork : project.getForks()) {
			if (fork.getAttributes().getId().equalsIgnoreCase(idFork)) return fork;
		}
		return null;
	}
	
	public Join findJoinById(Project project, String idJoin) {
		for (Join join : project.getJoins()) {
			if (join.getAttributes().getId().equalsIgnoreCase(idJoin)) return join;
		}
		return null;
	}
	
	public String findTypeById(Project project, String id){
		for (State state : project.getStates()) {
			if (state.getAttributes().getId().equalsIgnoreCase(id)) return state.getAttributes().getType();
		}
		for (Fork fork : project.getForks()) {
			if (fork.getAttributes().getId().equalsIgnoreCase(id)) return fork.getAttributes().getType(); 			
		}
		for (Join join : project.getJoins()) {
			if (join.getAttributes().getId().equalsIgnoreCase(id)) return join.getAttributes().getType();
		}
		return null;
	}
		
	public Transition makeTransition(State destiny) {	
		if (destiny==null) return null;
		Transition trans = new Transition();
		trans.setG("");  //si pongo nada no pasa nada

		trans.setTo(destiny.getProperties().getName().length()>0?destiny.getProperties().getName():destiny.getAttributes().getName());		
		if (HARD_STATES.contains(destiny.getAttributes().getType())) {
			trans.setTo(destiny.getAttributes().getType());
		}
		return trans;
	}
	
	public Transition makeForkTransition(Fork destiny) {	
		if (destiny==null) return null;
		Transition trans = new Transition();
		trans.setG("");

		trans.setTo(destiny.getProperties().getName().length()>0?destiny.getProperties().getName():destiny.getAttributes().getName());		
		if (HARD_STATES.contains(destiny.getAttributes().getType())) {
			trans.setTo(destiny.getAttributes().getType());
		}
		return trans;
	}
	
	public Transition makeJoinTransition(Join destiny) {	
		if (destiny==null) return null;
		Transition trans = new Transition();
		trans.setG("");

		trans.setTo(destiny.getProperties().getName().length()>0?destiny.getProperties().getName():destiny.getAttributes().getName());		
		if (HARD_STATES.contains(destiny.getAttributes().getType())) {
			trans.setTo(destiny.getAttributes().getType());
		}
		return trans;
	}
	
	public Task makeTask(Project project, State state) {
		Task task = new Task();
		task.setName(state.getProperties().getName());
		task.setType(state.getProperties().getTypeState());
		String stateType = state.getAttributes().getType(); 		
		if (HARD_STATES.contains(stateType)) {
			task.setName(stateType);
		}
		
		int x = state.getAttributes().getPosition().getX();
		int y = state.getAttributes().getPosition().getY();
		task.setX(x>0?x:5);
		task.setY(y>0?y*2:5);
		
		task.getTransitions().addAll(findTransitions(project,state.getAttributes().getId()));
		
		if (AUTOLOOP_STATES.contains(task.getName())) {
			task.getTransitions().add(makeTransition(state));
		}
		
		return task;
	}
	
	public ForkTag makeFork(Project project, Fork fork) {
		ForkTag forkTag = new ForkTag();
		forkTag.setName(fork.getProperties().getName());

//		String stateType = state.getAttributes().getType(); 		
//		if (HARD_STATES.contains(stateType)) {
//			task.setName(stateType);
//		}
		
		int x = fork.getAttributes().getPosition().getX();
		int y = fork.getAttributes().getPosition().getY();
		forkTag.setX(x>0?x:5);
		forkTag.setY(y>0?y*2:5);
		
		forkTag.getTransitions().addAll(findTransitions(project,fork.getAttributes().getId()));
		
//		if (AUTOLOOP_STATES.contains(task.getName())) {
//			task.getTransitions().add(makeTransition(state));
//		}
		
		return forkTag;
	}
	
	public JoinTag makeJoin(Project project, Join join) {
		JoinTag joinTag = new JoinTag();
		joinTag.setName(join.getProperties().getName());

//		String stateType = state.getAttributes().getType(); 		
//		if (HARD_STATES.contains(stateType)) {
//			task.setName(stateType);
//		}
		
		int x = join.getAttributes().getPosition().getX();
		int y = join.getAttributes().getPosition().getY();
		joinTag.setX(x>0?x:5);
		joinTag.setY(y>0?y*2:5);
		
		joinTag.getTransitions().addAll(findTransitions(project,join.getAttributes().getId()));
		
//		if (AUTOLOOP_STATES.contains(task.getName())) {
//			task.getTransitions().add(makeTransition(state));
//		}
		
		return joinTag;
	}
	
	public Map<String,Object> makeModelMap(Project project) {
		Task    task    = null;
		ForkTag forkTag = null;
		JoinTag joinTag = null;
		
		List<Task>    tasks = new ArrayList<>();
		List<ForkTag> forks = new ArrayList<>();
		List<JoinTag> joins = new ArrayList<>();
		
		for (State state : project.getStates()) {
			task = makeTask(project, state);
			tasks.add(task);
		}
		
		for (Fork fork : project.getForks()) {
			forkTag = makeFork(project, fork);
			forks.add(forkTag);
		}
		
		for (Join join : project.getJoins()) {
			joinTag = makeJoin(project, join);
			joins.add(joinTag);
		}
		
		Task start = makeTask(project, project.getStartState());
		start.setName("Inicio");

		Task end = makeTask(project, project.getEndState());
		end.setName("Cierre");
					
		Map<String, Object> dataModel = new HashMap<>();
		dataModel.put("projDescription", project.getDescription());
		dataModel.put("projAuthor", project.getAuthor());
		dataModel.put("projName", StringUtil.camelName(project.getName()));
		dataModel.put("projVersion", project.getVersion());
		dataModel.put("start", start);
		dataModel.put("end", end);
		dataModel.put("lstTask", tasks);
		dataModel.put("lstFork", forks);
		dataModel.put("lstJoin", joins);

		return dataModel;
	}
	
	@SuppressWarnings("deprecation")
	public void createJpdlFile(Project project, String directory) throws IOException, TemplateException {
		String workflowName =StringUtil.camelName(project.getName()); 
		String jpdl = "/spring/taskTemplate/configJPDL.ftl";
		Map<String, Object> dataModel = makeModelMap(project);
		dataModel.put("stringUtil", StringUtil.class);
		
		Configuration cfg = new Configuration();
		cfg.setClassForTemplateLoading(this.getClass(), "/");
		if(TypeWorkFlow.STATE.equals(project.getTypeWorkFlow())){
			jpdl = "/spring/stateTemplate/configStateJPDL.ftl";
		}
		Template temp = cfg.getTemplate(jpdl);
		String directoryDestino = directory+File.separator+"resources";
		String jpdlFile = String.format("%s/%s.jpdl.xml", directoryDestino, workflowName);
		FileUtils.forceMkdir(new File(directoryDestino));
		
		FileOutputStream fos = new FileOutputStream(new File(jpdlFile));
		Writer consoleWriter = new OutputStreamWriter(fos);
		temp.process(dataModel, consoleWriter);
		consoleWriter.flush();
		consoleWriter.close();
		fos.close();
	}
	
}