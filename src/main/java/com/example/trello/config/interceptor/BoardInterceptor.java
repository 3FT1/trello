package com.example.trello.config.interceptor;import com.example.trello.config.auth.UserDetailsImpl;import com.example.trello.config.auth.UserDetailsServiceImpl;import com.example.trello.user.User;import com.example.trello.user.UserRepository;import com.example.trello.workspace.WorkSpaceRepository;import com.example.trello.workspace.Workspace;import com.example.trello.workspace_member.WorkspaceMember;import com.example.trello.workspace_member.WorkspaceMemberRepository;import com.example.trello.workspace_member.WorkspaceMemberRole;import jakarta.servlet.http.HttpServletRequest;import jakarta.servlet.http.HttpServletResponse;import lombok.RequiredArgsConstructor;import org.springframework.security.core.context.SecurityContextHolder;import org.springframework.security.core.userdetails.UserDetails;import org.springframework.stereotype.Component;import org.springframework.web.servlet.HandlerInterceptor;@RequiredArgsConstructor@Componentpublic class BoardInterceptor implements HandlerInterceptor {    private final WorkspaceMemberRepository workspaceMemberRepository;    private final WorkSpaceRepository workSpaceRepository;    @Override    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {        if (request.getMethod().equals("POST")) {            return false;        }        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();        User user = userDetails.getUser();        Workspace workspace = workSpaceRepository.findByIdOrElseThrow(user.getId());        WorkspaceMember workspaceMember = workspaceMemberRepository.findByUserIdAndWorkspaceIdOrElseThrow(user.getId(), workspace.getId());        if (workspaceMember.getRole() != WorkspaceMemberRole.WORKSPACE) {            throw new RuntimeException();        }        return true;    }}