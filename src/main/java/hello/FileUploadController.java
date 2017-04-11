package hello;

import hello.search.FileSearchService;
import hello.storage.StorageFileNotFoundException;
import hello.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class FileUploadController {

    private final StorageService storageService;
    private final FileSearchService fileSearchService;
    
    @Autowired
    public FileUploadController(StorageService storageService, FileSearchService fileSearchService) {
        this.storageService = storageService;
        this.fileSearchService = fileSearchService;
    }
    
    @RequestMapping("/")
    public String listUploadedFiles(Model model) throws IOException {
    	
        return "jsp/home";
    }
    @RequestMapping("/Submit.html")
    public String listUploadedFiles1(Model model) throws IOException {
    	
        return "jsp/Submit";
    }
    @RequestMapping("/CONTACT.html")
    public String listUploadedFiles2(Model model) throws IOException {
    	
        return "jsp/CONTACT";
    }
    @RequestMapping("/hcmute.html")
    public String listUploadedFiles3(Model model) throws IOException {
    	
        return "jsp/hcmute";
    }
    @RequestMapping("/home.html")
    public String listUploadedFiles5(Model model) throws IOException {
    	
        return "jsp/home";
    }
    @RequestMapping("/icsse.html")
    public String listUploadedFiles4(Model model) throws IOException {
    	
        return "jsp/icsse";
    }
    @RequestMapping("/carforpaper.html")
    public String listUploadedFiles6(Model model) throws IOException {
    	
        return "jsp/carforpaper";
    }
    @RequestMapping("/Registration.html")
    public String listUploadedFiles7(Model model) throws IOException {
    	
        return "jsp/Registration";
    }
    @RequestMapping("/KNS.html")
    public String listUploadedFiles8(Model model) throws IOException {
    	
        return "jsp/KNS";
    }
   
	

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+file.getFilename()+"\"")
                .body(file);
    }
      
    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        storageService.store(file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/";
    }
    
    @GetMapping("/search")
    public String searchFiles(@RequestParam("fileName") String name, Model model) throws IOException {
    	model.addAttribute("result",fileSearchService.searchFile(name));
        return "uploadForm";
    }
    
    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}
