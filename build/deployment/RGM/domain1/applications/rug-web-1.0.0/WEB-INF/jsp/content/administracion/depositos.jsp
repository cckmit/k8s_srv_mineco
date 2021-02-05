<%@ taglib prefix="s" uri="/struts-tags"%>

<div class="section"></div>
<main>
	<div class="container-fluid">
		<div class="row">
			<div class="col s2"></div>
			<div class="col s8">
				<div class="card">
					<div class="card-content">
						<div class="row">
							<div class="col s8">
								<span class="card-title">Depósitos</span>
							</div>
						</div>
						<div class="row">
							<table>
								<thead>
									<tr>
										<th>Fecha</th>
										<th>Transaccion</th>
										<th>Persona</th>
										<th>Monto</th>
									</tr>
								</thead>
								<tbody>
								<s:iterator value="boletas">
									<tr>
										<td><s:date name="fechaHora" format="dd/MM/yyyy" /></td>
										<td><s:property value="idTransaccion"/></td>
										<td><s:property value="identificador"/></td>
										<td>Q. <s:property value="getText('{0, number, #, ##0.00}', {monto})" /></td>
										<td class="right">
											<a href="#" class="btn waves-effect indigo" onclick="resultadoBoleta(<s:property value="idTransaccion"/>, 'A')">Aprobar</a>
											<a href="#" class="btn waves-effect red darken-4" onclick="resultadoBoleta(<s:property value="idTransaccion"/>, 'R')">Rechazar</a>
										</td>
									</tr>
								</s:iterator>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
			<div class="col s2"></div>
		</div>
	</div>
</main>
<div class="section"></div>
