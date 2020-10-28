package kr.co.Controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.Command.BCommand;
import kr.co.Command.BContentCommand;
import kr.co.Command.BDeleteCommand;
import kr.co.Command.BListCommand;
import kr.co.Command.BModifyCommand;
import kr.co.Command.BReplyCommand;
import kr.co.Command.BReplyViewCommand;
import kr.co.Command.BWriteCommand;


@Controller
public class BController
{
	BCommand command = null;

	
	@RequestMapping("/")
	public String home()
	{
		return "redirect:list";
	}
	
	// 전체 게시글 조회
	@RequestMapping("/list")
	public String list(Model model)
	{
		System.out.println("list()");
		command = new BListCommand();
		command.execute(model);
		
		return "list";
	}
	
	// 글쓰기 View
	@RequestMapping("write_view")
	public String write_view(Model model)
	{
		System.out.println("write_view()");
		
		return "write_view";
	}
	
	
	// 글쓰기 Proecess
	@RequestMapping("/write")
	public String write(HttpServletRequest request, Model model)
	{
		System.out.println("write()");
		
		model.addAttribute("request", request);
		command = new BWriteCommand();
		command.execute(model);
		
		return "redirect:list";
	}
	
	// 해당 게시글 조회
	@RequestMapping("/content_view")
	public String content_view(HttpServletRequest request, Model model)
	{
		System.out.println("content_view()");
		
		model.addAttribute("request", request);
		command = new BContentCommand();
		command.execute(model);
		
		return "content_view";
	}
	
	
	// 글 수정
	@RequestMapping(value="/modify", method = RequestMethod.POST)
	public String modify(HttpServletRequest request, Model model)
	{
		System.out.println("modify()");
		
		model.addAttribute("request", request);
		command = new BModifyCommand();
		command.execute(model);
		
		return "redirect:list";
	}
	
	// 댓글 View
	@RequestMapping("/reply_view")
	public String reply_view(HttpServletRequest request, Model model)
	{
		System.out.println("reply_view()");
		
		model.addAttribute("request", request);
		command = new BReplyViewCommand();
		command.execute(model);
		
		return "reply_view";
	}
	
	// 댓글 Proecess
	@RequestMapping(value="/reply", method = RequestMethod.POST)
	public String reply(HttpServletRequest request, Model model)
	{
		System.out.println("reply()");
		
		model.addAttribute("request", request);
		command = new BReplyCommand();
		command.execute(model);
		
		return "redirect:list";
	}
	
	// 글 삭제 Proecess
	@RequestMapping("/delete")
	public String delete(HttpServletRequest request, Model model)
	{
		System.out.println("delete()");
		
		model.addAttribute("request", request);
		command = new BDeleteCommand();
		command.execute(model);
		
		return "redirect:list";
	}
	
	
	
}
