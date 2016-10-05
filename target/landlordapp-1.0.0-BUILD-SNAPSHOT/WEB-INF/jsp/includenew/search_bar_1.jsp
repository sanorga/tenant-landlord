<form class="search_form" action="" method="POST" role="form">
    <div class="section-search">
        <div class="srch-bx">
            <span class="srch-icon" for=""><i id="seach_icon" class="fa fa-search"></i></span>
            <input id="search_input_box" type="text" class="form-control form-control-sm" placeholder="Search...">
        </div>
        <div class="srch-bx">
            <label for="">Item</label>
            <input type="text" class="form-control form-control-sm" id="" placeholder="Report number">
        </div>
        <div class="srch-bx">
            <label for="">Operator</label>
            <input type="text" class="form-control form-control-sm" id="" placeholder="Contains">
        </div>
        <div class="srch-bx">
            <label for="">Value</label>
            <input type="text" class="form-control form-control-sm" id="" placeholder="">
        </div>
        <div class="match-options srch-bx">
            <div class="act-btns">
                <a href="#add" class="btn btn-default">Add</a>
                <a href="#delete" class="btn btn-default">Delete</a>
            </div>
            <div class="rdio-opts drk-bg">
                <input type="radio" id="c1" name="cc" />
                <label for="c1"><i></i>Match ALL conditions</label>
                <input type="radio" id="c2" name="cc" />
                <label for="c2"><i></i>Match ANY conditions</label>
            </div>
        </div>
        <a href="#"><img src="img/btn_search.png" alt="Search" class="srch_btn"></a>
    </div>
</form> <!-- .section-search.row -->