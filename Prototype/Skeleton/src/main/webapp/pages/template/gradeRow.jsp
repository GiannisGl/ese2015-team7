<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<tr id = "tr${gradeNumber}">
	<td>
		<form:select path="signupForm.grades[${gradeNumber}].university" id="field-University${gradeNumber}" tabindex="1">
    		<form:option value='None' label="Select University"/>
    		<form:options items="${universities}" itemValue="universityName"/>
    	</form:select>
	</td>
	<td>
   		<form:select path="signupForm.grades[${gradeNumber}].subject" id="field-Subject${gradeNumber}" tabindex="1">
    		<form:option value='None' label="Select Subject"/>
   		</form:select>
   	</td>
   	<td>
   		<form:select path="signupForm.grades[${gradeNumber}].course" id="field-Course${gradeNumber}" tabindex="1">
    		<form:option value='None' label="Select Course"/>
   		</form:select>
   	</td>
   	<td>
   		<form:select path="signupForm.grades[${gradeNumber}].grade" tabindex="1">
    		<form:option value='None' label="Select Grade"/>
    		<form:option value="1"/>
    		<form:option value="2"/>
    		<form:option value="3"/>
    		<form:option value="4"/>
    		<form:option value="5"/>
    		<form:option value="6"/>
   		</form:select>
   	</td>
   	<td>
    	<button type="button" onclick="removeGradeRow(${gradeNumber})">Remove</button>
    </td>
</tr>
<form:hidden path="signupForm.grades[${gradeNumber}].remove" />