package com.TaskManageMentProject.Security;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.TaskManageMentProject.Enum.Permission;
import com.TaskManageMentProject.Enum.Role;

public class RolePermissionConfig {
	
	public static Map<Role,Set<Permission>>getPermission(){
		
		Map<Role,Set<Permission>> map=new HashMap<>();
		map.put(Role.ADMIN,new HashSet<>(Arrays.asList(Permission.ISSUE_VIEW,
				                     Permission.ISSUE_CREATE,
				                     Permission.ISSUE_EDIT,
				                     Permission.ISSUE_DELETE,
				                     Permission.COMMENT_ADD,
				                     Permission.COMMENT_DELETE,
				                     Permission.USER_MANAGE)));
		
		map.put(Role.MANAGER,new HashSet<>(Arrays.asList(Permission.ISSUE_VIEW,
				Permission.ISSUE_CREATE,
                Permission.ISSUE_EDIT,
                Permission.ISSUE_DELETE,
                Permission.COMMENT_ADD)));
		
		map.put(Role.DEVELOPER,new HashSet<>(Arrays.asList(Permission.ISSUE_VIEW,
				Permission.ISSUE_EDIT,
				Permission.COMMENT_ADD)));
		
		map.put(Role.TESTER,new HashSet<>(Arrays.asList(Permission.ISSUE_VIEW,
				Permission.COMMENT_ADD)));
		return map;
		
		
	}

}
