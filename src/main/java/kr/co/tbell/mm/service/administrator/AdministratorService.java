package kr.co.tbell.mm.service.administrator;

import kr.co.tbell.mm.dto.administrator.ReqCreateAdministrator;
import kr.co.tbell.mm.dto.administrator.ResCreateAdministrator;

import javax.management.InstanceAlreadyExistsException;

public interface AdministratorService {
    ResCreateAdministrator createAdministrator(ReqCreateAdministrator reqCreateAdministrator)
            throws InstanceAlreadyExistsException;
}
