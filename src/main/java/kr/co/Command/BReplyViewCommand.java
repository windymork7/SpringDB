package kr.co.Command;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import kr.co.DAO.BDao;
import kr.co.DTO.BDto;

public class BReplyViewCommand implements BCommand
{

	@Override
	public void execute(Model model)
	{
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		int bId = Integer.parseInt(request.getParameter("bId"));
		
		BDao dao = new BDao();
		BDto dto = dao.reply_view(bId);
		dao.step(bId, dto.getbIndent()-1);
		
		model.addAttribute("reply_view", dto);
	}

}
