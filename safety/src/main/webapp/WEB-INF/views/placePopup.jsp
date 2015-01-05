<%@ page pageEncoding="utf-8" contentType="text/html;charset=UTF-8"	language="java"%>
	<table>
			<tr>
			<th>
			<div>공종</div>			
			<select id="worktype_pop"  class="siteSelectBox" onchange="setCateogry(this.id, 'category1_pop')">
					<option selected="selected" >공종</option>					
			</select>
			</th>
			<th>
			<div>중분류</div>
			<select id="category1_pop"  class="siteSelectBox" onchange="setCateogry(this.id, 'category2_pop')">
					<option selected="selected" >중분류</option>					
			</select></th>
			<th>
			<div>소분류</div>
			<select id="category2_pop" class="siteSelectBox" onchange="setCodeBySelect (this.id, 'workname_pop')">
					<option selected="selected" >소분류</option>					
			</select>
			</th>
		</tr>
		<tr>
			<th>작업명</th>
			<td colspan="2"><select id="workname_pop" class="siteSelectBox">
				<option value="" selected="selected" >작업명 선택</option>					
			</select>			
			</td>
		</tr>
	</table>	
	<div class="signup"><img src="images/btn_signup.png"
				class="signupImg" alt="등록하기" onclick="confirmCode()"
				onmouseover="this.src='images/btn_signup_over.png'"
				onmouseout="this.src='images/btn_signup.png'"
				style="cursor: pointer">
	</div>
