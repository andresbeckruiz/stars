<#assign content>

<div class="center">


  <h1 id="header1"> Stars! </h1>

  <h4> Please input commands using the following format: number "starname" OR number x-cord y-cord z-cord. The number
  represents the number of neighbors or the radius to search for. </h4>
  <div class="search_forms">
    <div>
      <p>
        <h4 class="search_headers"> Search by Neighbors </h4>
        <form method="POST" action="/neighbors">
        <div class="container">
          <textarea name="text" id="neighbor_text"></textarea><br>
          <label for="neighbor_text"/><br>
          <input type="checkbox" id="naive_neighbor" name="method" value="naive">
          <label for="naive_neighbor">Naive Neighbors</label><br>
          <br>
        </div>
        <input type="submit">
        </form>
      </p>
    </div>
    <div>
      <p>
        <h4 class="search_headers"> Search by Radius </h4>
        <form method="POST" action="/radius">
        <div class="container">
          <textarea name="text" label="radius text area" id="radius_text"></textarea><br>
          <label for="radius_text"/><br>
          <input type="checkbox" label="radius checkbox" id="naive_radius" name="method" value="naive">
          <label for="naive_radius">Naive Radius</label><br>
          <br>
        </div>
        <input type="submit">
        </form>
      </p>
    </div>
  </div>

  <h2> Stars Found: </h2>

  ${results}

</div>

</#assign>
<#include "main.ftl">