<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="container">
    <a class="btn btn-info" href="/profile" role="button">Назад</a>
    <br>
    <br/>
    <div class="row">
        <div class="col-md-6 col-md-offset-3 ">
            <div class="panel panel-primary">
                <div class="panel-heading">Оплата</div>
                <div class="panel-body">
                    <div class="card">
                        <form:form method="post" action="/payment?id=${id}" enctype="multipart/form-data">
                        <div>
                            <div class="d-flex pt-3 pl-3">
                                <div>
                                    <img src="my-visa.png" width="60" height="80"/>
                                </div>
                                <div class="mt-3 pl-2">
                                    <span class="name">
                                        <label>
                                            Имя пользователя: ${user.username}
                                        </label>
                                    </span>
                                </div>
                            </div>
                            <div class="py-2 px-3">
                                <div class="second pl-2 d-flex py-2">
                                    <div class="border-left pl-2">
                                        <span class="head">
                                            <label>
                                            К оплате: ${price}
                                        </label>
                                        </span>
                                    </div>
                                    <br>
                                </div>
                                <div class="d-flex justify-content-between px-3 pt-4 pb-3">
                                </div>
                                <button type="submit" class="btn btn-primary btn-lg">Оплатить</button>
                                </form:form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>