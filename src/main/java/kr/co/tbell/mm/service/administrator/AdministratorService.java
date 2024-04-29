package kr.co.tbell.mm.service.administrator;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.tbell.mm.dto.administrator.ReqCreateAdministrator;
import kr.co.tbell.mm.dto.administrator.ReqLogin;
import kr.co.tbell.mm.dto.administrator.ResCreateAdministrator;
import kr.co.tbell.mm.dto.administrator.ResLogin;

import javax.management.InstanceAlreadyExistsException;

public interface AdministratorService {
    ResCreateAdministrator createAdministrator(ReqCreateAdministrator reqCreateAdministrator)
            throws InstanceAlreadyExistsException;

    ResLogin login(ReqLogin reqLogin);
}
