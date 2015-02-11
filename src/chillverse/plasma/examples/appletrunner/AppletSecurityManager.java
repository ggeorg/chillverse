package chillverse.plasma.examples.appletrunner;

import java.io.FilePermission;
import java.net.SocketPermission;
import java.security.Permission;
import java.security.SecurityPermission;

class AppletSecurityManager extends SecurityManager {
  private boolean plugin;

  AppletSecurityManager(boolean plugin) {
    this.plugin = plugin;
  }

  public void checkPermission(Permission permission) {
    if (permission == null) {
      throw new IllegalArgumentException("permission is null");
    }

    // FIXME: we need to restrict this.
    if (permission instanceof FilePermission) {
      return;
    }

    // FIXME: we need to restrict this.
    if (permission instanceof SecurityPermission) {
      return;
    }

    // Needed to create a class loader for each codebase.
    if (permission.getName().equals("createClassLoader")) {
      return;
    }

    // FIXME: we need to allow access to codebase here.

    if (permission instanceof SocketPermission 
        || permission instanceof RuntimePermission) {
      return;
    }

    if (!plugin && permission.getName().equals("exitVM")) {
      return;
    }

    // Reject all other permissions.
    throw new SecurityException();
  }
}
