/**
 * ẩn hiện trình độ tiếng nhật
 */
function showHide() {
	var check = document.getElementById("levelJapan");
	if (check.style.display == 'none') {
		check.style.display = 'block'
	} else {
		check.style.display = 'none'
	}
}
/**
 * Xứ lí tạo form confirm xác nhận delete user
 *
 *@param userId id người dùng muốn xóa
 *@param message câu hỏi xác nhận xóa
 */
function confirmDelete(userId, message) {
	var cf = confirm(message);
	if (cf == true) {
	var form = document.createElement('form');
	form.setAttribute('action', "deleteUser.do");
	form.setAttribute('method', "POST");

	var input = document.createElement("input");
	input.setAttribute('type', "hidden");
	input.setAttribute('name', "userId");
	input.setAttribute('value', userId);
	form.appendChild(input);
	document.body.appendChild(form);
	form.submit();
	} else {
	
	}

}