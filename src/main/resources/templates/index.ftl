<!DOCTYPE html>
<html lang="de">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="icon" href="img/fav-icon.png" type="image/x-icon" />
        <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
        <title>${model.applicant.firstname!"firstname"} ${model.applicant.lastname!"lastname"} / PROLICHT Bewerbung / </title>

        <!-- Icon css link -->
        <link href="vendors/material-icon/css/materialdesignicons.min.css" rel="stylesheet">
        <link href="css/font-awesome.min.css" rel="stylesheet">
        <link href="vendors/linears-icon/style.css" rel="stylesheet">
        <!-- Bootstrap -->
        <link href="css/bootstrap.min.css" rel="stylesheet">

        <!-- Extra plugin css -->
        <link href="vendors/owl-carousel/assets/owl.carousel.css" rel="stylesheet">
        <link href="vendors/animate-css/animate.css" rel="stylesheet">

        <link href="css/style.css" rel="stylesheet">
        <link href="css/responsive.css" rel="stylesheet">

        <!-- <link rel="stylesheet" href="css/colors/default.css" title="default"> -->
        <link rel="stylesheet" href="css/colors/blue.css" title="blue">

        <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
        <![endif]-->
    </head>
    <body class="light_bg" data-spy="scroll" data-target="#bs-example-navbar-collapse-1" data-offset="80" data-scroll-animation="true">

        <div id="preloader">
            <div id="preloader_spinner">
                <div class="spinner"></div>
            </div>
        </div>

        <!--================ Frist hader Area =================-->
        <header class="header_area">
            <div class="container">
                <nav class="navbar navbar-default">
                    <!-- Brand and toggle get grouped for better mobile display -->
                    <div class="navbar-header">
                        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                            <span class="sr-only">Navigation</span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                        </button>
                        <a class="navbar-brand" href="index.html"></a>
                    </div>

                    <!-- Collect the nav links, forms, and other content for toggling -->
                    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                        <ul class="nav navbar-nav navbar-right">
                            <#if model.applicant??>
                            <li class="active"><a href="#about">Über mich </a></li>
                            </#if>
                            <li><a href="#service">Services</a></li>
                            <#if model.knowledges??>
                            <li><a href="#skill">Kenntnisse</a></li>
                            </#if>
                            <#if model.jobs??>
                            <li><a href="#jobs">Anstellungen</a></li>
                            </#if>
                            <#if model.education??>
                            <li><a href="#education">Ausbildung</a></li>
                            </#if>
                            <#if model.hobbies??>
                            <li><a href="#hobby">Interessen</a></li>
                            </#if>
                            <#if model.applicant??>
                            <li><a href="#contact">Kontakt</a></li>
                            </#if>
                        </ul>
                    </div><!-- /.navbar-collapse -->
                </nav>
            </div>
        </header>
        <!--================End Footer Area =================-->

        <!--================Total container Area =================-->
        <div class="container main_container">
            <div class="content_inner_bg row m0">
                <#if model.applicant??>
                <section class="about_person_area pad" id="about">
                    <div class="row">
                        <div class="col-md-5">
                            <div class="person_img">
                                <img src="./photo/download/3" alt="">
                                <a class="download_btn" href="./photo/download/4"><span>Download Lebenslauf</span></a>
                            </div>
                        </div>
                        <div class="col-md-7">
                            <div class="row person_details">
                                <h3>Hi, ich bin <span>${model.applicant.firstname!"firstname"} ${model.applicant.lastname!"lastname"}</span></h3>
                                <h4>${model.applicant.title!"title"}</h4>
                                <p>${model.applicant.intro!"intro"}</p>
                                <div class="person_information">
                                    <ul>
                                        <li>Adresse</li>
                                        <li>Telefon</li>
                                        <li>Email</li>
                                        <li>Website</li>
                                        <li>Geburtsdatum</li>
                                        <li>Geburtsort</li>
                                    </ul>
                                    <ul>
                                        <li>${model.applicant.street!"street"}, ${model.applicant.postcode!"postcode"} ${model.applicant.city!"city"}</li>
                                        <li><a href="tel:${model.applicant.phone!"phone"}">${model.applicant.phone!"phone"}</a></li>
                                        <li><a href="mailto:${model.applicant.email!"email"}">${model.applicant.email!"email"}</a></li>
                                        <li><a href="https://prolicht.n4v.eu">prolicht.n4v.eu</a></li>
                                        <li>${model.applicant.birthdate?date!"birthdate"}</li>
                                        <li>${model.applicant.birthplace!"birthplace"}</li>
                                    </ul>
                                </div>
                                <ul class="social_icon">
                                    <li><a href="https://github.com/kolbma"><i class="fa fa-github"></i></a></li>
                                    <li><a href="https://blog.n4v.eu"><i class="fa fa-rss"></i></a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </section>
                </#if>
                <section class="service_area" id="service">
                    <div class="main_title">
                        <h2>Services</h2>
                    </div>
                    <div class="service_inner row">
                        <div class="col-md-6">
                            <div class="service_item wow fadeIn animated">
                                <i class="fa fa-github" aria-hidden="true"></i>
                                <a href="https://github.com/kolbma/prolicht"><h4>Github Projekt Sourcecode</h4></a>
                                <p>Der Sourcecode zur SpringBoot-basierenden Webapplikation dieser Webseite findet sich auf <a href="https://github.com/kolbma/prolicht">Github</a>.</p>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="service_item wow fadeIn animated">
                                <i class="fa fa-sitemap" aria-hidden="true"></i>
                                <a href="./swagger-ui.html"><h4>Swagger REST API</h4></a>
                                <p>Die Applikation stellt eine <a href="./swagger-ui.html">REST API</a> bereit, über die der meiste Seiteninhalt mit einem internen REST-Client abgerufen werden. Diese Anwendungsarchitektur dient Demonstrationszwecken.</p>
                            </div>
                        </div>
                    </div>
                </section>
                <#if model.knowledges??>
                <section class="myskill_area pad" id="skill">
                    <div class="main_title">
                        <h2>Kenntnisse</h2>
                    </div>
                    <div class="row">
                        <#list model.knowledges as category, knowledges>
                        <div class="col-md-6 wow fadeInUp animated">
                            <div class="skill_text">
                                <h4>${category!"category"}</h4>
                                <#--  <p></p>  -->
                            </div>
                            <div class="skill_item_inner">
                                <#list knowledges as knowledge>
                                <div class="single_skill">
                                    <h4>${knowledge.name!"knowledge.name"}</h4>
                                    <div class="progress">
                                        <div class="progress-bar" role="progressbar" aria-valuenow="${knowledge.sequence!100}" aria-valuemin="0" aria-valuemax="100">
                                            <div class="progress_parcent"><span class="counter">${knowledge.sequence!100}</span>%</div>
                                        </div>
                                    </div>
                                </div>
                                </#list>
                            </div>
                        </div>
                        </#list>
                    </div>
                </section>
                </#if>
                <#if model.jobs??>
                <section class="education_area pad" id="jobs">
                    <div class="main_title">
                        <h2>Anstellungen</h2>
                    </div>
                    <div class="education_inner_area">
                        <#list model.jobs as job>
                        <#if job.dateresolution == "YEAR">
                            <#assign dstring="yyyy">
                        <#elseif job.dateresolution == "MONTH">
                            <#assign dstring="MM.yyyy">
                        <#elseif job.dateresolution == "DAY">
                            <#assign dstring="dd.MM.yyyy">
                        </#if>
                        <div class="education_item wow fadeInUp animated" data-line="${job.title[0..0]?upper_case}">
                            <h6>${job.startdate?string(dstring)}<#if job.startdate?datetime != job.enddate?datetime>-${job.enddate?string(dstring)}</#if></h6>
                            <h4>${job.title!"job.title"}</h4>
                            ${job.description!"<p>job.description</p>"}
                        </div>
                        </#list>
                    </div>
                </section>
                </#if>
                <#if model.education??>
                <section class="education_area pad" id="education">
                    <div class="main_title">
                        <h2>Ausbildung</h2>
                    </div>
                    <div class="education_inner_area">
                        <#list model.education as edu>
                        <#if edu.dateresolution == "YEAR">
                            <#assign dstring="yyyy">
                        <#elseif edu.dateresolution == "MONTH">
                            <#assign dstring="MM.yyyy">
                        <#elseif edu.dateresolution == "DAY">
                            <#assign dstring="dd.MM.yyyy">
                        </#if>
                        <div class="education_item wow fadeInUp animated" data-line="${edu.title[0..0]?upper_case}">
                            <h6>${edu.startdate?string(dstring)}<#if edu.startdate?datetime != edu.enddate?datetime>-${edu.enddate?string(dstring)}</#if></h6>
                            <h4>${edu.title!"edu.title"}</h4>
                            ${edu.description!"<p>edu.description</p>"}
                        </div>
                        </#list>
                    </div>
                </section>
                </#if>
                <#if model.hobbies??>
                <section class="service_area" id="hobby">
                    <div class="main_title">
                        <h2>Interessen</h2>
                    </div>
                    <div class="service_inner row">
                        <#list model.hobbies as category, hobbies>
                        <#assign icon><#switch category>
                                <#case "IT">
                                    code
                                    <#break>
                                <#case "Natur">
                                    leaf
                                    <#break>
                                <#case "Sport">
                                    futbol-o
                                    <#break>
                                <#default>
                                    rocket
                                    <#break>
                        </#switch></#assign>
                        <div class="col-md-6">
                            <div class="service_item wow fadeIn animated">
                                <i class="fa fa-${icon?trim}" aria-hidden="true"></i>
                                <a href="#"><h4>${category!"category"}</h4></a>
                                <#list hobbies as hobby>
                                <p>${hobby!"hobby"}</p>
                                </#list>
                            </div>
                        </div>
                        </#list>
                    </div>
                </section>
                </#if>
                <#if model.applicant??>
                <section class="contact_area pad" id="contact">
                    <div class="main_title">
                        <h2>Kontakt</h2>
                    </div>
                    <div class="row">
                        <div class="col-md-5">
                            <div class="left_contact_details wow fadeInUp animated">
                                <div class="contact_title">
                                    <h3>Kontakt Info</h3>
                                </div>
                                ${model.applicant.contactInfo!"contactInfo"}
                            </div>
                        </div>
                        <div class="col-md-2">
                        </div>
                        <div class="col-md-5">
                            <div class="left_contact_details wow fadeInUp animated">
                                <div class="media">
                                    <div class="media-left">
                                        <i class="fa fa-map-marker"></i>
                                    </div>
                                    <div class="media-body">
                                        <h4>Adresse</h4>
                                        <p>${model.applicant.street!"street"}, ${model.applicant.postcode!"postcode"} ${model.applicant.city!"city"}</p>
                                    </div>
                                </div>
                                <div class="media">
                                    <div class="media-left">
                                        <i class="fa fa-phone"></i>
                                    </div>
                                    <div class="media-body">
                                        <h4>Telefon</h4>
                                        <p><a href="tel:${model.applicant.phone!"phone"}">${model.applicant.phone!"phone"}</a></p>
                                    </div>
                                </div>
                                <div class="media">
                                    <div class="media-left">
                                        <i class="fa fa-envelope-o"></i>
                                    </div>
                                    <div class="media-body">
                                        <h4>Email</h4>
                                        <p><a href="mailto:${model.applicant.email!"email"}">${model.applicant.email!"email"}</a></p>
                                    </div>
                                </div>
                                <div class="media">
                                    <div class="media-left">
                                        &nbsp;
                                    </div>
                                    <div class="media-body">
                                        <h4>&nbsp;</h4>
                                        <p>&nbsp;</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                        </div>
                    </div>
                </section>
                </#if>
            </div>
        </div>
        <!--================End Total container Area =================-->

        <!--================footer Area =================-->
        <footer class="footer_area">
            <div class="footer_inner">
                <div class="container">
                    <ul class="social_icon">
                        <li><a href="https://github.com/kolbma"><i class="fa fa-github"></i></a></li>
                        <li><a href="https://blog.n4v.eu"><i class="fa fa-rss"></i></a></li>
                    </ul>
                </div>
            </div>
            <div class="footer_copyright">
                <div class="container">
                    <div class="pull-right">
                        <ul class="nav navbar-nav navbar-right">
                            <#if model.applicant??>
                            <li class="active"><a href="#about">&Uuml;ber mich </a></li>
                            </#if>
                            <li><a href="#service">Services</a></li>
                            <#if model.knowledges??>
                            <li><a href="#skill">Kenntnisse</a></li>
                            </#if>
                            <#if model.jobs??>
                            <li><a href="#jobs">Anstellungen</a></li>
                            </#if>
                            <#if model.education??>
                            <li><a href="#education">Ausbildung</a></li>
                            </#if>
                            <#if model.hobbies??>
                            <li><a href="#hobby">Interessen</a></li>
                            </#if>
                            <#if model.applicant??>
                            <li><a href="#contact">Kontakt</a></li>
                            </#if>
                        </ul>
                    </div>
                    <div class="pull-left">
                        <h5><!-- Template is licensed under CC BY 3.0. -->
    Copyright &copy;<script>document.write(new Date().getFullYear());</script> All rights reserved | CC BY 3.0 | This template is made with <i class="fa fa-heart-o" aria-hidden="true"></i> by Colorlib and modified by Markus Kolb
    <!-- Template is licensed under CC BY 3.0. --></h5>
                    </div>
                </div>
            </div>
        </footer>
        <!--================End footer Area =================-->

        <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
        <script src="js/jquery-2.1.4.min.js"></script>
        <!-- Include all compiled plugins (below), or include individual files as needed -->
        <script src="js/bootstrap.min.js"></script>
        <!-- Extra plugin js -->
        <script src="vendors/counter-up/waypoints.min.js"></script>
        <script src="vendors/counter-up/jquery.counterup.min.js"></script>
        <script src="vendors/isotope/imagesloaded.pkgd.min.js"></script>
        <script src="vendors/isotope/isotope.pkgd.min.js"></script>
        <script src="vendors/owl-carousel/owl.carousel.min.js"></script>

        <!-- <script src="vendors/style-switcher/styleswitcher.js"></script> -->
        <!-- <script src="vendors/style-switcher/switcher-active.js"></script> -->

        <script src="vendors/animate-css/wow.min.js"></script>

        <!--gmaps Js-->
        <!-- <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCjCGmQ0Uq4exrzdcL6rvxywDDOvfAu6eE"></script> -->
        <!-- <script src="js/gmaps.min.js"></script> -->

        <!-- contact js -->
        <!-- <script src="js/jquery.form.js"></script> -->
        <!-- <script src="js/jquery.validate.min.js"></script> -->
        <!-- <script src="js/contact.js"></script> -->

        <script src="js/theme.js"></script>
    </body>
</html>
