package br.com.fiap.EpicTask.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.fiap.EpicTask.model.Task;
import br.com.fiap.EpicTask.repository.TaskRepository;

@Controller
@RequestMapping("/task")
public class TaskController {
	
	@Autowired
	private TaskRepository repository;
	
	@GetMapping
	public ModelAndView tasks() {
		List<Task> tasks = repository.findAll();
		ModelAndView modelAndView = new ModelAndView("task");
		modelAndView.addObject("tasks", tasks);
		return modelAndView;
	}
	
	@PostMapping
	public String save(@Valid Task task, BindingResult result, RedirectAttributes attribute) {
		if(result.hasErrors()) {
			return "task_new";
		}
		repository.save(task);
		attribute.addFlashAttribute("msg", "Task criada com sucesso");
		return "redirect:task";
	}
	
	@RequestMapping("new")
	public String createTask(Task task) {
		return "task_new";
	}
	
	@GetMapping("{id}")
	public ModelAndView editTaskForm(@PathVariable Long id) {
		Optional<Task> task = repository.findById(id);
		ModelAndView modelAndView = new ModelAndView("task_edit");
		modelAndView.addObject("task", task);
		return modelAndView;
	}
	
	@PostMapping("update")
	public String updateTask(@Valid Task task, BindingResult result) {
		if(result.hasErrors()) {
			return "task_edit";
		}
		repository.save(task);
		return "redirect:/task";
	}
	
	@RequestMapping("delete/{id}")
	public String removeTask(@PathVariable Long id, RedirectAttributes attribute) {
		repository.deleteById(id);
		attribute.addFlashAttribute("msg", "Task removida com sucesso");
		return "redirect:/task";
	}
	
}