package kr.co.Command;

import java.util.ArrayList;

import org.springframework.ui.Model;

import kr.co.DAO.BDao;
import kr.co.DTO.BDto;

public class BListCommand implements BCommand
{
	@Override
	public void execute(Model model)
	{
		BDao dao = new BDao();
		ArrayList<BDto> boardList = dao.boardList();
		model.addAttribute("list", boardList);
		
	}
}
