package io.github.hingbong.cloudnote.controller;

import io.github.hingbong.cloudnote.entity.Note;
import io.github.hingbong.cloudnote.service.NoteService;
import io.github.hingbong.cloudnote.util.JsonResponse;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * note controller
 *
 * @author Hingbong
 */
@RestController
@RequestMapping("/note")
public class NoteController extends BaseController {

  private NoteService noteService;

  @PostMapping
  public JsonResponse<Void> addNote(HttpSession session, Note note) {
    Integer uid = getUidFromSession(session);
    String username = getUsernameFromSession(session);
    noteService.addNote(uid, username, note);
    return JsonResponse.success("添加成功");
  }

  @GetMapping("/notes/shared/all")
  public JsonResponse<List<Note>> getAllSharedNotes() {
    List<Note> noteList = noteService.getAllSharedNotes();
    return JsonResponse.success(noteList);
  }

  @GetMapping("/shared/note/one/{nid}")
  public JsonResponse<Note> getSharedNoteByNid(@PathVariable Integer nid) {
    Note noteList = noteService.getNoteByNid(nid);
    return JsonResponse.success(noteList);
  }

  @GetMapping("/{nbId}")
  public JsonResponse<List<Note>> getNotesByNbId(HttpSession session, @PathVariable Integer nbId) {
    Integer uid = getUidFromSession(session);
    List<Note> notes = noteService.getNoteByNotebook(uid, nbId);
    return JsonResponse.success(notes);
  }

  @GetMapping("/note/{nid}")
  public JsonResponse<Note> getNoteByNid(HttpSession session, @PathVariable Integer nid) {
    Integer uid = getUidFromSession(session);
    Note note = noteService.getNoteByNid(uid, nid);
    return JsonResponse.success(note);
  }

  @PutMapping
  public JsonResponse<Void> modifyNote(HttpSession session, Note note) {
    Integer uid = getUidFromSession(session);
    String username = getUsernameFromSession(session);
    noteService.modifyNote(uid, username, note);
    return JsonResponse.success();
  }

  @PutMapping("/{nid}/shared")
  public JsonResponse<Void> setShared(HttpSession session, @PathVariable("nid") Integer nid) {
    String username = getUsernameFromSession(session);
    Integer uid = getUidFromSession(session);
    noteService.setShared(uid, username, nid);
    return JsonResponse.success();
  }

  @PutMapping("/{nid}/unshared")
  public JsonResponse<Void> unsetShared(HttpSession session, @PathVariable("nid") Integer nid) {
    String username = getUsernameFromSession(session);
    Integer uid = getUidFromSession(session);
    noteService.unsetShared(uid, username, nid);
    return JsonResponse.success();
  }

  @DeleteMapping("/{nid}")
  public JsonResponse<Void> deleteNote(HttpSession session, @PathVariable("nid") Integer nid) {
    String username = getUsernameFromSession(session);
    Integer uid = getUidFromSession(session);
    noteService.deleteNote(uid, username, nid);
    return JsonResponse.success();
  }

  @Autowired
  private void setNoteService(NoteService noteService) {
    this.noteService = noteService;
  }
}
