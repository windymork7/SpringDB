package kr.co.Command;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import kr.co.DAO.BDao;
import kr.co.DTO.BDto;

public class BContentCommand implements BCommand
{
	@Override
	public void execute(Model model)
	{
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		int bId = Integer.parseInt(request.getParameter("bId"));
		
		BDao dao = new BDao();
		BDto dto = dao.contentView(bId);
		dao.hit(bId, dto.getbHit());
		
		
		model.addAttribute("content_view", dto);
	}
}
