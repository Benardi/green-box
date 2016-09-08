package org.ufcg.si.controllers;

import java.io.IOException;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.ufcg.si.beans.requests.AddFileRequestBody;
import org.ufcg.si.beans.requests.AddFolderRequestBody;
import org.ufcg.si.beans.requests.EditFileRequestBody;
import org.ufcg.si.beans.requests.ShareFileRequestBody;
import org.ufcg.si.exceptions.GreenboxException;
import org.ufcg.si.models.Notification;
import org.ufcg.si.models.User;
import org.ufcg.si.repositories.UserService;
import org.ufcg.si.repositories.UserServiceImpl;
import org.ufcg.si.util.ExceptionHandler;
import org.ufcg.si.util.ServerConstants;
import org.ufcg.si.util.permissions.FilePermission;
import org.ufcg.si.util.requests.RenameFileRequestBody;
import org.ufcg.si.util.requests.RenameFolderRequestBody;

/**
 * This controller class uses JSON data format to be the 
 * endpoint of requests related to directories of users
 * on the server-side.
 * 
 * <strong>This controller needs to a valid token to be accessed.</strong>
 */
@RestController
@RequestMapping(ServerConstants.SERVER_REQUEST_URL + ServerConstants.USERS_DIRECTORY_REQUEST_URL)
public class UsersActionsController {
	private UserService userService;
	
	@RequestMapping(value = "/newfolder", 
					method = RequestMethod.POST,
					produces = MediaType.APPLICATION_JSON_VALUE,
					consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> addFolder(@RequestBody AddFolderRequestBody requestBody) throws ServletException {
		try {
			ExceptionHandler.checkAddFolderBody(requestBody);
		
			User dbUser = userService.findByUsername(requestBody.getUser().getUsername());
			ExceptionHandler.checkUserInDatabase(dbUser);

			dbUser.addFolder(requestBody.getFolderName(), requestBody.getFolderPath());
			User updatedUser = userService.update(dbUser);
		
			return new ResponseEntity<>(updatedUser, HttpStatus.OK);
		} catch(GreenboxException gbe) {
			gbe.printStackTrace();
			throw new ServletException("Request error while trying to create new folder... " + gbe.getMessage());
		} catch (DataAccessException dae) {
			dae.printStackTrace();
			throw new ServletException("Request error while trying to create new folder... " + dae.getMessage());
		}
	}
	
	@RequestMapping(value = "/newfile", 
					method = RequestMethod.POST,
					produces = MediaType.APPLICATION_JSON_VALUE,
					consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> addFile(@RequestBody AddFileRequestBody requestBody) throws ServletException {
		try {
			ExceptionHandler.checkAddFileBody(requestBody);
			
			User dbUser = userService.findByUsername(requestBody.getUser().getUsername());
			ExceptionHandler.checkUserInDatabase(dbUser);
			
			dbUser.addFile(requestBody.getFileName(), requestBody.getFileExtension(), requestBody.getFileContent(), requestBody.getFilePath());
			User updatedUser = userService.update(dbUser);
			
			return new ResponseEntity<>(updatedUser, HttpStatus.OK);
		} catch(GreenboxException gbe) {
			gbe.printStackTrace();
			throw new ServletException("Request error while trying to create new file... " + gbe.getMessage());
		} catch (DataAccessException dae) {
			dae.printStackTrace();
			throw new ServletException("Request error while trying to create new file... " + dae.getMessage());
		} catch(IOException ioe) {
			ioe.printStackTrace();
			throw new ServletException("Request error while trying to create new file... " + ioe.getMessage());
		}
	}
	
	@RequestMapping(value = "/editfile",
					method = RequestMethod.PUT,
					produces = MediaType.APPLICATION_JSON_VALUE,
					consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> editFile(@RequestBody EditFileRequestBody requestBody) throws Exception{
		try {
			//ExceptionHandler.checkEditFileBody(requestBody);
			
			User dbUser = userService.findByUsername(requestBody.getUser().getUsername());
			ExceptionHandler.checkUserInDatabase(dbUser);
			
			String fileName = requestBody.getClickedFile().getName();
			
			if (!requestBody.getFileName().equals(requestBody.getClickedFile().getName())) {
				dbUser.editFileName(requestBody.getFileName(), requestBody.getClickedFile().getName(), requestBody.getFilePath());
				fileName = requestBody.getFileName();
			}
			
			if (!requestBody.getFileExtension().equals(requestBody.getClickedFile().getExtension())) {
				dbUser.editFileExtension(requestBody.getFileExtension(), fileName, requestBody.getFilePath());
			}
			
			if (!requestBody.getFileContent().equals(requestBody.getClickedFile().getContent())) {
				dbUser.editFileContent(requestBody.getFileName(), requestBody.getFileContent(), requestBody.getFilePath());
			}
			
			User updateUser = userService.update(dbUser);
			return new ResponseEntity<>(updateUser, HttpStatus.OK);
		} catch(GreenboxException gbe) {
			gbe.printStackTrace();
			throw new ServletException("Request error while trying to edit file... " + gbe.getMessage());
		} catch (DataAccessException dae) {
			dae.printStackTrace();
			throw new ServletException("Request error while trying to edit file... " + dae.getMessage());
		} catch(IOException ioe) {
			ioe.printStackTrace();
			throw new ServletException("Request error while trying to edit file... " + ioe.getMessage());
		}
	}
	

	
	private User editFileContent(EditFileRequestBody requestBody, User dbUser) throws Exception{
		dbUser.editFileContent(requestBody.getFileName(), requestBody.getFileContent(), requestBody.getFilePath());
		User updateUser = userService.update(dbUser);
		
		return updateUser;
	}
	
	@RequestMapping(value = "/renamefolder", 
					method = RequestMethod.POST,
					produces = MediaType.APPLICATION_JSON_VALUE,
					consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> renameFolder(@RequestBody RenameFolderRequestBody requestBody)throws Exception{
		try{
			
			User dbUser = userService.findByUsername(requestBody.getUser().getUsername());
		
			ExceptionHandler.checkUserInDatabase(dbUser);
			
			dbUser.editFolderName(requestBody.getNewName(), requestBody.getOldName(), requestBody.getFolderPath());
			User updateUser = userService.update(dbUser);
		
			return new ResponseEntity<>(updateUser, HttpStatus.OK);
		} catch(GreenboxException gbe) {
			gbe.printStackTrace();
			throw new ServletException("Request error while trying to rename folder... " + gbe.getMessage());
		} catch (DataAccessException dae) {
			dae.printStackTrace();
			throw new ServletException("Request error while trying to rename folder... " + dae.getMessage());
		} 
	}
	
	@RequestMapping(value = "/renamefile", 
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> renameFile(@RequestBody RenameFileRequestBody requestBody) throws Exception {
		try {
			
			User dbUser = userService.findByUsername(requestBody.getUser().getUsername());
		
			ExceptionHandler.checkUserInDatabase(dbUser);
			
			dbUser.editFileName(requestBody.getNewName(), requestBody.getOldName(), requestBody.getFolderPath());
			User updateUser = userService.update(dbUser);
		
			return new ResponseEntity<>(updateUser, HttpStatus.OK);
		} catch(GreenboxException gbe) {
			gbe.printStackTrace();
			throw new ServletException("Request error while trying to rename folder... " + gbe.getMessage());
		} catch (DataAccessException dae) {
			dae.printStackTrace();
			throw new ServletException("Request error while trying to rename folder... " + dae.getMessage());
		} 
	}
	
	@RequestMapping(value = "/sharefile", 
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> shareFile(@RequestBody ShareFileRequestBody requestBody) throws Exception {
		try {
			User sendingUser = userService.findByUsername(requestBody.getUser().getUsername());
			User receivingUser = userService.findByUsername(requestBody.getUserSharedWith().getUsername());
		
			ExceptionHandler.checkUserInDatabase(sendingUser);
			ExceptionHandler.checkUserInDatabase(receivingUser);
			
			sendingUser.shareFile(receivingUser, requestBody.getName(), requestBody.getFolderPath(), FilePermission.valueOfIgnoreCase(requestBody.getPermissionLevel()));
			User updateUser = userService.update(sendingUser);
			userService.update(receivingUser);
		
			return new ResponseEntity<>(updateUser, HttpStatus.OK);
		} catch(GreenboxException gbe) {
			gbe.printStackTrace();
			throw new ServletException("Request error while trying to rename folder... " + gbe.getMessage());
		} catch (DataAccessException dae) {
			dae.printStackTrace();
			throw new ServletException("Request error while trying to rename folder... " + dae.getMessage());
		} 
	}
	
	@RequestMapping(value = "/notifications", 
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Iterable<Notification>> listNotifications(@RequestBody User requestBody) throws Exception {
		try {
			User dbUser = userService.findByUsername(requestBody.getUsername());
			
			ExceptionHandler.checkUserInDatabase(dbUser);
			Iterable<Notification> notifications = dbUser.listNotifications();
			System.out.println(notifications);
			return new ResponseEntity<>(notifications, HttpStatus.OK);
		} catch(GreenboxException gbe) {
			gbe.printStackTrace();
			throw new ServletException("Request error while trying to rename folder... " + gbe.getMessage());
		} catch (DataAccessException dae) {
			dae.printStackTrace();
			throw new ServletException("Request error while trying to rename folder... " + dae.getMessage());
		} 
	}
	
	@Autowired
	public void setUserService(UserServiceImpl userServiceImpl) {
		this.userService = userServiceImpl;
	}
}
