<%@ page pageEncoding="utf-8" contentType="text/html;charset=UTF-8"	language="java"%>

	<table class="work_signup">
	<tr>
		<th>공&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;종</th>
		<th>대&nbsp;&nbsp;&nbsp;분&nbsp;&nbsp;&nbsp;류</th>
		<th>소&nbsp;&nbsp;&nbsp;분&nbsp;&nbsp;&nbsp;류</th>	
	</tr>
	<tr>
		<th>	
			<select id="worktype_pop"  class="siteSelectBox colspanInput" onchange="clearSelect('category1_pop','category2_pop','workname_pop');setCateogry(this.id, 'category1_pop')"></select>
		</th>
		<th>
			<select id="category1_pop"  class="siteSelectBox colspanInput" onchange="clearSelect('category2_pop','workname_pop');setCateogry(this.id, 'category2_pop')"></select>							
		</th>
		<th>
			<select id="category2_pop" class="siteSelectBox colspanInput" onchange="clearSelect('workname_pop');setCodeBySelect (this.id, 'workname_pop')"></select>
		</th>
	</tr>
	<tr>
		<th>작&nbsp;&nbsp;&nbsp;업&nbsp;&nbsp;&nbsp;명</th>
		<td colspan="2"><select id="workname_pop" class="siteSelectBox" onchange="$('#popupOKBtn').show();" style="width:85%;">
			<option value="" selected="selected" >작업명 선택</option>					
		</select>			
		</td>
	</tr>
	</table>	
	<span  class="signup">
		<span id="popupOKBtn" class="btn_typ02"  style="display:inline-block;" onclick="confirmCode()">선택 </span>
	</span>
	

