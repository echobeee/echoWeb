<!DOCTYPE html>
<html>
        <head>
        </head>
        <body style="margin: 0; padding: 0;">
            <table border="0" cellpadding="0" cellspacing="0" width="100%">    
                <tbody><tr>
                    <td style="padding: 10px 0 30px 0;">
                        <table align="center" border="0" cellpadding="0" cellspacing="0" width="600" style="border: 1px solid #cccccc; border-collapse: collapse;">
                            <tbody><tr>
                                <td align="center" bgcolor="#70bbd9" style="padding: 40px 0 30px 0; color: #153643; font-size: 28px; font-weight: bold; font-family: Arial, sans-serif;">
                                    <img src="cid:email" alt="Creating Email Magic" width="300" height="230" style="display: block;"/>
                                </td>
                            </tr>
                            <tr>
                                <td bgcolor="#ffffff" style="padding: 40px 30px 40px 30px;">
                                    <table border="0" cellpadding="0" cellspacing="0" width="100%">
                                        <tbody><tr>
                                            <td style="color: #153643; font-family: Arial, sans-serif; font-size: 24px;">
                                                <b>
                                                   用户id:　${user.userId}
                                                    <br/>
                                                    用户昵称:　${user.userNickname}        
                                                    <br/>
                                                    内容:<br/>
                                                </b>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td style="padding: 20px 0 30px 0; color: #153643; font-family: Arial, sans-serif; font-size: 16px; line-height: 20px;">
                                                　　　您好，${user.userNickname}，请
	                    	 					<#if expTime??>                           
	                                               	在<font color="red">${expTime}</font>前到以下网站进行帐号激活<br>
                                                <#else >
                                                	到以下网站完成密码重设<br>
                                                </#if>
                                                <a href="${activeUrl }">${activeUrl}</a>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <table border="0" cellpadding="0" cellspacing="0" width="100%">
                                                    <tbody><tr>
                                                        <td width="260" valign="top">
                                                            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                                                                <tbody><tr>
                                                                    <td>
                                                                        <img src="cid:phone" alt="" width="100%" height="140" style="display: block;"/>
                                                                    </td>
                                                                </tr>
                                                            </tbody></table>
                                                        </td>
                                                        <td style="font-size: 0; line-height: 0;" width="20">
                                                        </td>
                                                        <td width="260" valign="top">
                                                            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                                                                <tbody><tr>
                                                                    <td>
                                                                        <img src="cid:clock" alt="" width="100%" height="140" style="display: block;"/>
                                                                    </td>
                                                                </tr>
                                                            </tbody></table>
                                                        </td>
                                                    </tr>
                                                </tbody></table>
                                            </td>
                                        </tr>
                                    </tbody></table>
                                </td>
                            </tr>
                            <tr>
                                <td bgcolor="#ee4c50" style="padding: 30px 30px 30px 30px;">
                                    <table border="0" cellpadding="0" cellspacing="0" width="100%">
                                        <tbody><tr>
                                            <td align="right" width="25%">
                                                <table border="0" cellpadding="0" cellspacing="0">
                                                    <tbody><tr>
                                                        <td style="font-family: Arial, sans-serif; font-size: 12px; font-weight: bold;">
                                                            <img src="cid:twitter" alt="Twitter" width="38" height="38" style="display: block;" border="0"/>
                                                        </td>
                                                        <td style="font-family: Arial, sans-serif; font-size: 12px; font-weight: bold;">
                                                            <img src="cid:facebook" alt="Facebook" width="38" height="38" style="display: block;" border="0"/>
                                                        </td>
                                                    </tr>
                                                </tbody></table>
                                            </td>
                                        </tr>
                                    </tbody></table>
                                </td>
                            </tr>
                        </tbody></table>
                    </td>
                </tr>
            </tbody></table>
        </body>
    </html>
