
package io.choerodon.admin.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.choerodon.mybatis.annotation.ModifyAudit;
import io.choerodon.mybatis.annotation.MultiLanguage;
import io.choerodon.mybatis.annotation.MultiLanguageField;
import io.choerodon.mybatis.annotation.VersionAudit;
import io.choerodon.mybatis.domain.AuditDomain;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.hzero.boot.platform.lov.annotation.LovValue;
import org.hzero.starter.keyencrypt.core.Encrypt;


@ModifyAudit
@VersionAudit
@MultiLanguage
@JsonInclude(Include.NON_NULL)
@Table(
    name = "iam_menu"
)
public class Menu extends AuditDomain {
    @Encrypt
    @Id
    @GeneratedValue
    private Long id;

    @NotNull(
        message = "error.menuCode.null",
        groups = {Menu.Valid.class}
    )
    @Pattern(
        regexp = "^[a-zA-Z0-9][a-zA-Z0-9-_./]*$",
        message = "error.menu.code.illegal",
        groups = {Menu.Valid.class}
    )
    @Length(
        max = 255
    )
    private String code;

    @NotNull(
        message = "error.menuName.null",
        groups = {Menu.Valid.class}
    )
    @MultiLanguageField
    @Length(
        max = 128
    )
    private String name;

    @Pattern(
        regexp = "^[a-zA-Z0-9]{0,30}$",
        message = "error.menu.quickIndexIllegal",
        groups = {Menu.Valid.class}
    )
    @Column(
        name = "h_quick_index"
    )
    private String quickIndex;

    @NotNull(
        message = "error.menuLevel.null",
        groups = {Menu.Valid.class}
    )
    @Column(
        name = "fd_level"
    )
    @LovValue(
        lovCode = "HIAM.RESOURCE_LEVEL",
        groups = {Menu.Valid.class}
    )
    private String level;

    @Encrypt
    private Long parentId;

    @NotNull(
        message = "error.menuType.null",
        groups = {Menu.Valid.class}
    )
    @LovValue(
        lovCode = "HIAM.MENU_TYPE"
    )
    private String type;

    private Integer sort;
    @Range(
        min = 0L,
        max = 1L,
        groups = {Menu.Valid.class}
    )
    private Integer isDefault;

    private String icon;
    private String route;
    @Range(
        min = 0L,
        max = 1L
    )
    @Column(
        name = "h_custom_flag"
    )
    private Integer customFlag;

    @Column(
        name = "h_tenant_id"
    )
    @NotNull(
        message = "error.tenantId.null",
        groups = {Menu.Valid.class}
    )
    @Encrypt
    private Long tenantId;
    @Range(
        min = 0L,
        max = 1L
    )
    @Column(
        name = "h_enabled_flag"
    )
    private Integer enabledFlag;
    @Column(
        name = "h_description"
    )
    private String description;
    @Column(
        name = "h_level_path"
    )
    private String levelPath;
    @Column(
        name = "h_virtual_flag"
    )
    @Range(
        min = 0L,
        max = 1L
    )
    private Integer virtualFlag;
    @Column(
        name = "h_controller_type"
    )
    @LovValue(
        lovCode = "HIAM.CONTROLLER_TYPE"
    )
    private String controllerType;
    @Column(
        name = "h_permission_type"
    )
    private String permissionType;
    @Transient
    private Integer editDetailFlag;
    @Transient
    private Integer newSubnodeFlag;
    @Transient
    private Integer psLeafFlag;
    @Transient
    private String checkedFlag;
    @Transient
    @JsonIgnore
    private Menu parentMenu;
    @Transient
    private String parentName;
    @Transient
    private List<Menu> subMenus;
    @Transient
    private String parentCode;

    @Encrypt
    @Transient
    private Long parentTenantId;

    @Transient
    private String zhName;
    @Transient
    private String enName;
    @Transient
    private String levelMeaning;
    @Transient
    private String typeMeaning;
    @Transient
    private String controllerTypeMeaning;
    @Transient
    private String inheritFlag;
    @Transient
    private String createFlag;
    @Transient
    private Integer shieldFlag;

    @Encrypt
    @Transient
    private String secGrpAclId;

    @Transient
    private String tenantName;

    public Menu() {
    }

    public Long getParentTenantId() {
        return this.parentTenantId;
    }

    public void setParentTenantId(Long parentTenantId) {
        this.parentTenantId = parentTenantId;
    }

    public String getInheritFlag() {
        return this.inheritFlag;
    }

    public void setInheritFlag(String inheritFlag) {
        this.inheritFlag = inheritFlag;
    }

    public String getCreateFlag() {
        return this.createFlag;
    }

    public void setCreateFlag(String createFlag) {
        this.createFlag = createFlag;
    }

    public Integer getShieldFlag() {
        return this.shieldFlag;
    }

    public void setShieldFlag(Integer shieldFlag) {
        this.shieldFlag = shieldFlag;
    }

    public String getSecGrpAclId() {
        return this.secGrpAclId;
    }

    public void setSecGrpAclId(String secGrpAclId) {
        this.secGrpAclId = secGrpAclId;
    }


    public void resetCode(String originParentCode, String nowParentCode) {
    }



    public String getParentName() {
        return this.parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public Integer getSort() {
        return this.sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuickIndex() {
        return this.quickIndex;
    }

    public void setQuickIndex(String quickIndex) {
        this.quickIndex = quickIndex;
    }

    public String getLevel() {
        return this.level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Long getParentId() {
        return this.parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getDefault() {
        return this.isDefault;
    }

    public void setDefault(Integer aDefault) {
        this.isDefault = aDefault;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getZhName() {
        return this.zhName;
    }

    public void setZhName(String zhName) {
        this.zhName = zhName;
    }

    public String getEnName() {
        return this.enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getLevelMeaning() {
        return this.levelMeaning;
    }

    public void setLevelMeaning(String levelMeaning) {
        this.levelMeaning = levelMeaning;
    }

    public String getTypeMeaning() {
        return this.typeMeaning;
    }

    public void setTypeMeaning(String typeMeaning) {
        this.typeMeaning = typeMeaning;
    }

    public String getRoute() {
        return this.route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public Integer getCustomFlag() {
        return this.customFlag;
    }

    public void setCustomFlag(Integer customFlag) {
        this.customFlag = customFlag;
    }

    public Long getTenantId() {
        return this.tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Integer getEnabledFlag() {
        return this.enabledFlag;
    }

    public void setEnabledFlag(Integer enabledFlag) {
        this.enabledFlag = enabledFlag;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Menu> getSubMenus() {
        return this.subMenus;
    }

    public void setSubMenus(List<Menu> subMenus) {
        this.subMenus = subMenus;
    }

    public Menu getParentMenu() {
        return this.parentMenu;
    }

    public void setParentMenu(Menu parentMenu) {
        this.parentMenu = parentMenu;
    }

    public String getLevelPath() {
        return this.levelPath;
    }

    public void setLevelPath(String levelPath) {
        this.levelPath = levelPath;
    }

    public Integer getVirtualFlag() {
        return this.virtualFlag;
    }

    public void setVirtualFlag(Integer virtualFlag) {
        this.virtualFlag = virtualFlag;
    }

    public String getControllerType() {
        return this.controllerType;
    }

    public void setControllerType(String controllerType) {
        this.controllerType = controllerType;
    }

    public Integer getEditDetailFlag() {
        return this.editDetailFlag;
    }

    public void setEditDetailFlag(Integer editDetailFlag) {
        this.editDetailFlag = editDetailFlag;
    }

    public Integer getNewSubnodeFlag() {
        return this.newSubnodeFlag;
    }

    public void setNewSubnodeFlag(Integer newSubnodeFlag) {
        this.newSubnodeFlag = newSubnodeFlag;
    }

    public Integer getPsLeafFlag() {
        return this.psLeafFlag;
    }

    public void setPsLeafFlag(Integer psLeafFlag) {
        this.psLeafFlag = psLeafFlag;
    }

    public String getCheckedFlag() {
        return this.checkedFlag;
    }

    public void setCheckedFlag(String checkedFlag) {
        this.checkedFlag = checkedFlag;
    }

    public String getControllerTypeMeaning() {
        return this.controllerTypeMeaning;
    }

    public void setControllerTypeMeaning(String controllerTypeMeaning) {
        this.controllerTypeMeaning = controllerTypeMeaning;
    }

    public String getPermissionType() {
        return this.permissionType;
    }

    public Menu setPermissionType(String permissionType) {
        this.permissionType = permissionType;
        return this;
    }

    public interface Valid {
    }

    public static enum ControllerType {
        HIDDEN("hidden"),
        DISABLED("disabled");

        private String type;

        private ControllerType(String type) {
            this.type = type;
        }

        public String type() {
            return this.type;
        }
    }
}
