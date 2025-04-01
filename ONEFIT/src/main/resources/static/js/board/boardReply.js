async function get1(noticeId) {

    const result = await axios.get(`/replies_board/board_notice_list/${noticeId}`)

    // console.log(result)
    // return result.data
    return result
}

async function get2(qnaId) {

    const result = await axios.get(`/replies_board/board_qa_list/${qnaId}`)

    // console.log(result)
    // return result.data
    return result
}

//공지사항게시판 댓글 목록 처리
async function getNoticeList({noticeId, page, size, goLast}) {

    const result = await axios.get(`/replies_board/board_notice_list/${noticeId}`,{params:{page, size}})

    if(goLast) {
        const total = result.data.total
        const lastPage = parseInt(Math.ceil(total/size))

        return getNoticeList({noticeId:noticeId, page:lastPage, size:size})
    }
    return result.data
}

//QnA게시판 댓글 목록 처리
async function getQnaList({qnaId, page, size, goLast}) {

    const result = await axios.get(`/replies_board/board_qa_list/${qnaId}`,{params:{page, size}})

    if(goLast) {
        const total = result.data.total
        const lastPage = parseInt(Math.ceil(total/size))

        return getQnaList({qnaId:qnaId, page:lastPage, size:size})
    }
    return result.data
}

async function addBoardReply(replyObj){
    const response = await axios.post(`/replies_board/`,replyObj)
    return response.data
}

async function getBoardReply(replyId){
    const response = await axios.get(`/replies_board/${replyId}`)
    return response.data
}

async function modifyBoardReply(replyObj){
    const response = await axios.put(`/replies_board/${replyObj.replyId}`,replyObj)
    return response.data
}

async function removeBoardReply(replyId){
    const response = await axios.delete(`/replies_board/${replyId}`)
    return response.data
}