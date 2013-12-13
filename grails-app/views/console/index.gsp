<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <title>ORM Console</title>
	<r:require modules="jquery,bootstrap,codemirror"/>
	<g:javascript>
		var scriptMirror;
		var outputMirror;
		var keyspaceMirror;

		function prevScript()
		{
			$.ajax({
				type: 'GET',
				url: '/console/prevScript',
				success: function(data, textStatus){
					if (data) {
						outputMirror.setValue('');
						scriptMirror.setValue(data);
					}
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {alert(errorThrown)},
				dataType: 'text'
			});
		}

		function nextScript()
		{
			$.ajax({
				type: 'GET',
				url: '/console/nextScript',
				success: function(data, textStatus){
					if (data) {
						outputMirror.setValue('');
						scriptMirror.setValue(data);
					}
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {alert(errorThrown)},
				dataType: 'text'
			});
		}

		function setColumnFamily(name)
		{
			var index = name.indexOf('_')
			var base = index > 0 ? name.slice(0,index) : name;
			//console.log(base);
			$('#column_family_name').html(name);
			$('#ColumnFamilies li.dropdown').removeClass('active');
			$('#' + base).addClass('active');
		}

		function executeScript()
		{
			var script = scriptMirror.getValue();
			var data = {script: script, columnFamily: $('#column_family_name').html()};
			var url = '/console/executeScript';

			$('#spinner').removeClass('hidden');
			outputMirror.setValue('');

			$.ajax({
				type: 'POST',
				url: url,
				data: data,
				success: function(data, textStatus){
					if (data.error) {
						outputMirror.setValue(data.error);
					}
					else {
						outputMirror.setValue(data.output);
					}

					if (data.columnFamily) {
						keyspaceMirror.setValue(data.columnFamily);
					}

					setColumnFamily(data.columnFamilyName);
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {alert(errorThrown)},
				dataType: 'json'
			});

			$('#spinner').addClass('hidden');

			return false;
		}

		function showColumnFamily(ctrl)
		{
			var data = {columnFamily: $(ctrl).html()};
			$.ajax({
				type: 'POST',
				url: '/console/showColumnFamily',
				data: data,
				success: function(data, textStatus){
					keyspaceMirror.setValue(data.columnFamily);
					setColumnFamily(data.columnFamilyName);
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {alert(errorThrown)},
				dataType: 'json'
			});
		}

		function showSource(name)
		{
			var data = {name: name};
			$.ajax({
				type: 'POST',
				url: '/console/showSource',
				data: data,
				success: function(data, textStatus){
					keyspaceMirror.setValue(data.source);
					setColumnFamily(data.name);
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {alert(errorThrown)},
				dataType: 'json'
			});
		}

		$(document).ready(function() {
			scriptMirror = CodeMirror.fromTextArea(document.getElementById('script'), {
				theme: 'eclipse',
				mode: 'groovy',
				indentWithTabs: true,
				indentUnit: 4,
				tabSize: 4,
				lineNumbers: true,
				matchBrackets: true
			});
			scriptMirror.setSize("100%",500);

			outputMirror = CodeMirror.fromTextArea(document.getElementById('output'), {
				theme: 'eclipse',
				mode: 'text/plain',
				lineNumbers: false,
				readOnly: true
			});
			outputMirror.setSize("100%",290);

			keyspaceMirror = CodeMirror.fromTextArea(document.getElementById('keyspace'), {
				theme: 'eclipse',
				mode: 'groovy',
				lineNumbers: false,
				readOnly: true
			});
			keyspaceMirror.setSize("100%",745);
		});
	</g:javascript>
</head>
<body>

<div id="keyspace_panel">
	<ul id="ColumnFamilies" class="nav nav-pills">
		<g:each in="${columnFamilies}" var="cf">
			<li id="${cf.key}" class="dropdown">
				<a href="#" class="dropdown-toggle" data-toggle="dropdown">
					${cf.key}
					<b class="caret"></b>
				</a>
				<ul class="dropdown-menu">
					<li><a href="#" onclick="showColumnFamily(this);">${cf.key}</a></li>
					<li><a href="#" onclick="showColumnFamily(this);">${cf.key}_IDX</a></li>
					<li><a href="#" onclick="showColumnFamily(this);">${cf.key}_CTR</a></li>
					<li><a href="#" onclick="showSource('${cf.key}');">${cf.key} Class</a></li>
				</ul>
			</li>
		</g:each>
	</ul>
	<h3 id="column_family_name">&nbsp;</h3>
	<div class="wrap"><textarea id="keyspace" name="output" rows="30" cols="80"></textarea></div>
</div>

<div id="script_panel">
	<form action="run" onclick="return false;">
		<input type="submit" value="&lt; Prev" onclick="return prevScript();"/>
		<input type="submit" value="Next &gt;" onclick="return nextScript();"/>
		<input type="submit" value="Run Script" onclick="return executeScript();"/>
		<span id="spinner" class="hidden"><img src="${resource(dir: 'images', file: 'spinner.gif')}" alt="Running"/></span>
	</form>
	<div class="wrap"><textarea id="script" name="script" rows="25" cols="80"></textarea></div>
</div>

<div id="output_panel">
	<div class="wrap"><textarea id="output" name="output" rows="10" cols="80"></textarea></div>
</div>

</body>
</html>