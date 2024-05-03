package kr.co.tbell.mm.service.administrator;

import kr.co.tbell.mm.dto.administrator.ReqCreateAdministrator;
import kr.co.tbell.mm.dto.administrator.ResCreateAdministrator;

public interface AdministratorService {
    ResCreateAdministrator createAdministrator(ReqCreateAdministrator reqCreateAdministrator);
}
