/**
 * list.jsp 스크립트
 */
$(function(){
	// 모달 창 띄우기
	checkModal(result);
	
	history.replaceState({},null,null);
	
	function checkModal(result){
		if(result===''||history.state){
			return;
		}
		
		if(parseInt(result)>0){
			$(".modal-body").html("게시글 "+parseInt(result)+" 번이 등록되었습니다.");
		}
		
		$("#myModal").modal("show");
	}
	
	
	//하단의 페이지 나누기 번호 클릭시 
	var actionForm = $("#actionForm");
	$(".paginate_button a").click(function(e){
		e.preventDefault();  //a 속성 중지
		
		//actionForm의 안의 pageNum의 값을 사용자가 선택한 번호로 변경
		actionForm.find("input[name='pageNum']").val($(this).attr("href"));
		
		//actionForm 보내기
		actionForm.submit();
	})
	
	//게시물 수 변경
	$("#amount").change(function(){
		//사용자가 선택한 게시물 수 가져오기
		let amount = $(this).val();
		
		//actionForm의 안의 amount의 값을 사용자가 선택한 번호로 변경
		actionForm.find("input[name='amount']").val(amount);
		
		//actionForm 보내기
		actionForm.submit();
	})
	
	//게시글 제목 클릭시
	$(".move").click(function(e){
		e.preventDefault(); //타이틀 a 속성 막기
		
		//actionForm에 bno 값을 추가하여 actionForm 보내기		
		actionForm.append("<input type='hidden' name='bno' value='"+$(this).attr('href')+"'>");
		actionForm.attr('action','read');
		actionForm.submit();		
	})
	
	//검색
	//type or keyword 가 값이 없는 경우 경고 메세지를 주기
	//둘 다 값이 있다면 submit 하기
	$(".btn-default").click(function(){
		//검색 폼 가져오기
		var searchForm = $("#searchForm");
		
		//type 가져오기
		var type = $("select[name='type']").val();
		
		//keyword 가져오기
		var keyword = $("input[name='keyword']").val();
		
		if(type===''){
			alert("검색 기준을 확인하세요");
			$("select[name='type']").focus();
			return false;
		}else if(keyword===''){
			alert("검색어를 확인하세요");
			$("input[name='keyword']").focus();
			return false;
		}
		
		//검색 처음에는 1page 보여주기
		searchForm.find("input[name='pageNum']").val("1");
		
		searchForm.submit();
	})
})
























